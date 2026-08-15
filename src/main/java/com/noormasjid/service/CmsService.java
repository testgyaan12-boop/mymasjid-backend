package com.noormasjid.service;

import com.noormasjid.entity.cms.*;
import com.noormasjid.entity.masjid.Masjid;
import com.noormasjid.entity.notification.AppNotification;
import com.noormasjid.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@Service
public class CmsService {

    private final PushNotificationService pushNotificationService;

    private final BrandingRepository brandingRepository;
    private final HomeAnnouncementRepository homeAnnouncementRepository;
    private final PrayerTimeRepository prayerTimeRepository;
    private final JumuahConfigRepository jumuahConfigRepository;
    private final RamadanConfigRepository ramadanConfigRepository;
    private final RamadanDayRepository ramadanDayRepository;
    private final JanazahRepository janazahRepository;
    private final GumshudaRepository gumshudaRepository;
    private final GeneralAnnouncementRepository generalAnnouncementRepository;
    private final DonationCauseRepository donationCauseRepository;
    private final MonthlyDonationRepository monthlyDonationRepository;
    private final ExpenseRepository expenseRepository;
    private final AboutServiceRepository aboutServiceRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MasjidRepository masjidRepository;
    private final AppNotificationRepository appNotificationRepository;

    public CmsService(PushNotificationService pushNotificationService,
                      BrandingRepository brandingRepository,
                      HomeAnnouncementRepository homeAnnouncementRepository,
                      PrayerTimeRepository prayerTimeRepository,
                      JumuahConfigRepository jumuahConfigRepository,
                      RamadanConfigRepository ramadanConfigRepository,
                      RamadanDayRepository ramadanDayRepository,
                      JanazahRepository janazahRepository,
                      GumshudaRepository gumshudaRepository,
                      GeneralAnnouncementRepository generalAnnouncementRepository,
                      DonationCauseRepository donationCauseRepository,
                      MonthlyDonationRepository monthlyDonationRepository,
                      ExpenseRepository expenseRepository,
                      AboutServiceRepository aboutServiceRepository,
                      TeamMemberRepository teamMemberRepository,
                      MasjidRepository masjidRepository,
                      AppNotificationRepository appNotificationRepository) {
        this.brandingRepository = brandingRepository;
        this.homeAnnouncementRepository = homeAnnouncementRepository;
        this.prayerTimeRepository = prayerTimeRepository;
        this.jumuahConfigRepository = jumuahConfigRepository;
        this.ramadanConfigRepository = ramadanConfigRepository;
        this.ramadanDayRepository = ramadanDayRepository;
        this.janazahRepository = janazahRepository;
        this.gumshudaRepository = gumshudaRepository;
        this.generalAnnouncementRepository = generalAnnouncementRepository;
        this.donationCauseRepository = donationCauseRepository;
        this.monthlyDonationRepository = monthlyDonationRepository;
        this.expenseRepository = expenseRepository;
        this.aboutServiceRepository = aboutServiceRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.masjidRepository = masjidRepository;
        this.appNotificationRepository = appNotificationRepository;
        this.pushNotificationService = pushNotificationService;
    }

    private Masjid getMasjid(Long masjidId) {
        return masjidRepository.findById(masjidId)
                .orElseThrow(() -> new IllegalArgumentException("Masjid not found"));
    }

    private void notifyMembers(Long masjidId, String title, String message, String type) {
        try {
            AppNotification n = new AppNotification();
            n.setMasjid(getMasjid(masjidId));
            n.setTitle(title);
            n.setMessage(message);
            n.setType(type == null ? "alert" : type);
            n.setIsRead(false);
            appNotificationRepository.save(n);
        } catch (Exception e) {
            // Never block the CMS save on notification persistence
        }
        try {
            pushNotificationService.sendToMasjid(masjidId, title, message, type);
        } catch (Exception e) {
            // Never block the CMS save on push delivery
        }
    }

    public Branding getBranding(Long masjidId) {
        return brandingRepository.findByMasjidId(masjidId).orElse(null);
    }

    public Branding saveBranding(Long masjidId, Branding branding) {
        Branding existing = brandingRepository.findByMasjidId(masjidId).orElse(new Branding());
        existing.setMasjid(getMasjid(masjidId));
        existing.setMasjidName(branding.getMasjidName());
        existing.setPrimaryColor(branding.getPrimaryColor());
        existing.setSecondaryColor(branding.getSecondaryColor());
        existing.setLogo(branding.getLogo());
        return brandingRepository.save(existing);
    }

    public HomeAnnouncement getHomeAnnouncement(Long masjidId) {
        return homeAnnouncementRepository.findByMasjidId(masjidId).orElse(null);
    }

    public HomeAnnouncement saveHomeAnnouncement(Long masjidId, HomeAnnouncement ha) {
        HomeAnnouncement existing = homeAnnouncementRepository.findByMasjidId(masjidId).orElse(new HomeAnnouncement());
        existing.setMasjid(getMasjid(masjidId));
        existing.setStatus(ha.getStatus());
        existing.setTitle(ha.getTitle());
        existing.setDescription(ha.getDescription());
        existing.setActive(ha.getActive());
        existing.setImage(ha.getImage());
        return homeAnnouncementRepository.save(existing);
    }

    public List<PrayerTime> getPrayerTimes(Long masjidId) {
        return prayerTimeRepository.findByMasjidIdOrderBySortOrderAsc(masjidId);
    }

    @Transactional
    public List<PrayerTime> savePrayerTimes(Long masjidId, List<PrayerTime> times) {
        List<PrayerTime> old = prayerTimeRepository.findByMasjidIdOrderBySortOrderAsc(masjidId);
        prayerTimeRepository.deleteByMasjidId(masjidId);
        Masjid masjid = getMasjid(masjidId);
        for (int i = 0; i < times.size(); i++) {
            PrayerTime pt = times.get(i);
            pt.setId(null);
            pt.setMasjid(masjid);
            pt.setSortOrder(i);
        }
        List<PrayerTime> saved = prayerTimeRepository.saveAll(times);
        notifyMembers(masjidId, "Prayer Times Updated",
                prayerDiffMessage(old, saved),
                "prayer-times");
        return saved;
    }

    private String prayerDiffMessage(List<PrayerTime> old, List<PrayerTime> now) {
        List<String> changes = new ArrayList<>();
        Map<String, PrayerTime> oldBy = new HashMap<>();
        for (PrayerTime p : old) {
            oldBy.put(normalizeName(p.getPrayerName()), p);
        }
        for (PrayerTime p : now) {
            PrayerTime o = oldBy.get(normalizeName(p.getPrayerName()));
            List<String> parts = new ArrayList<>();
            if (o == null) {
                parts.add("Azaan " + p.getAzaanTime() + ", Iqamah " + p.getPrayerTime());
                changes.add(p.getPrayerName() + " added: " + String.join(", ", parts));
                continue;
            }
            String oldAzaan = o.getAzaanTime() == null ? "" : o.getAzaanTime();
            String oldTime = o.getPrayerTime() == null ? "" : o.getPrayerTime();
            String newAzaan = p.getAzaanTime() == null ? "" : p.getAzaanTime();
            String newTime = p.getPrayerTime() == null ? "" : p.getPrayerTime();
            if (!newAzaan.equals(oldAzaan)) {
                parts.add("Azaan " + oldAzaan + " → " + newAzaan);
            }
            if (!newTime.equals(oldTime)) {
                parts.add("Iqamah " + oldTime + " → " + newTime);
            }
            if (!parts.isEmpty()) {
                changes.add(p.getPrayerName() + ": " + String.join(", ", parts));
            }
        }
        if (changes.isEmpty()) {
            return "No prayer time changes";
        }
        return String.join(" | ", changes);
    }

    private String normalizeName(String name) {
        if (name == null) return "";
        return name.trim().toLowerCase();
    }

    public JumuahConfig getJumuah(Long masjidId) {
        return jumuahConfigRepository.findByMasjidId(masjidId).orElse(null);
    }

    public JumuahConfig saveJumuah(Long masjidId, JumuahConfig config) {
        JumuahConfig existing = jumuahConfigRepository.findByMasjidId(masjidId).orElse(new JumuahConfig());
        existing.setMasjid(getMasjid(masjidId));
        existing.setPrayerTime(config.getPrayerTime());
        existing.setAzaanTime(config.getAzaanTime());
        JumuahConfig saved = jumuahConfigRepository.save(existing);
        notifyMembers(masjidId, "Jumuah Prayer Time Updated",
                "Friday prayer updated" + (config.getPrayerTime() == null ? "" : " to " + config.getPrayerTime()),
                "jumuah");
        return saved;
    }

    public RamadanConfig getRamadan(Long masjidId) {
        return ramadanConfigRepository.findByMasjidId(masjidId).orElse(null);
    }

    public RamadanConfig saveRamadan(Long masjidId, RamadanConfig config) {
        RamadanConfig existing = ramadanConfigRepository.findByMasjidId(masjidId).orElse(new RamadanConfig());
        existing.setMasjid(getMasjid(masjidId));
        existing.setTaraweeh(config.getTaraweeh());
        existing.setNote(config.getNote());
        existing.setIftarMessage(config.getIftarMessage());
        existing.setFitraRate(config.getFitraRate());
        return ramadanConfigRepository.save(existing);
    }

    public List<RamadanDay> getRamadanDays(Long masjidId) {
        return ramadanDayRepository.findByMasjidIdAndIsDeletedOrderByDayNoAsc(masjidId, 0);
    }

    @Transactional
    public List<RamadanDay> saveRamadanDays(Long masjidId, List<RamadanDay> days) {
        ramadanDayRepository.deleteByMasjidId(masjidId);
        Masjid masjid = getMasjid(masjidId);
        for (RamadanDay d : days) {
            if (d.getDayNo() == null) continue;
            d.setId(null);
            d.setMasjid(masjid);
        }
        return ramadanDayRepository.saveAll(days);
    }

    public List<Janazah> getJanazahs(Long masjidId) {
        return janazahRepository.findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(masjidId, 0);
    }

    public Janazah saveJanazah(Long masjidId, Janazah janazah) {
        janazah.setMasjid(getMasjid(masjidId));
        janazah.setActive(true);
        janazah.setActivatedAt(LocalDateTime.now());
        Janazah saved = janazahRepository.save(janazah);
        notifyMembers(masjidId, "Janazah Alert",
                "Janazah: " + (saved.getTitle() == null ? "Prayer announced" : saved.getTitle()),
                "janazah");
        return saved;
    }

    public void deleteJanazah(Long id) {
        Janazah j = janazahRepository.findById(id).orElseThrow();
        j.setIsDeleted(1);
        janazahRepository.save(j);
    }

    public Janazah toggleJanazahActive(Long id) {
        Janazah j = janazahRepository.findById(id).orElseThrow();
        boolean newActive = !Boolean.TRUE.equals(j.getActive());
        j.setActive(newActive);
        if (newActive) {
            j.setActivatedAt(LocalDateTime.now());
        }
        return janazahRepository.save(j);
    }

    public List<Gumshuda> getGumshudas(Long masjidId) {
        return gumshudaRepository.findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(masjidId, 0);
    }

    public Gumshuda saveGumshuda(Long masjidId, Gumshuda gumshuda) {
        gumshuda.setMasjid(getMasjid(masjidId));
        gumshuda.setActive(true);
        gumshuda.setActivatedAt(LocalDateTime.now());
        Gumshuda saved = gumshudaRepository.save(gumshuda);
        notifyMembers(masjidId, "Missing Person Alert",
                "Missing: " + (saved.getTitle() == null ? "Please look out" : saved.getTitle()),
                "missing");
        return saved;
    }

    public void deleteGumshuda(Long id) {
        Gumshuda g = gumshudaRepository.findById(id).orElseThrow();
        g.setIsDeleted(1);
        gumshudaRepository.save(g);
    }

    public Gumshuda toggleGumshudaActive(Long id) {
        Gumshuda g = gumshudaRepository.findById(id).orElseThrow();
        boolean newActive = !Boolean.TRUE.equals(g.getActive());
        g.setActive(newActive);
        if (newActive) {
            g.setActivatedAt(LocalDateTime.now());
        }
        return gumshudaRepository.save(g);
    }

    public List<GeneralAnnouncement> getAnnouncements(Long masjidId) {
        return generalAnnouncementRepository.findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(masjidId, 0);
    }

    public GeneralAnnouncement saveAnnouncement(Long masjidId, GeneralAnnouncement a) {
        a.setMasjid(getMasjid(masjidId));
        a.setActive(true);
        a.setActivatedAt(LocalDateTime.now());
        GeneralAnnouncement saved = generalAnnouncementRepository.save(a);
        notifyMembers(masjidId, "New Announcement",
                saved.getTitle() == null ? "Check the latest update" : saved.getTitle(),
                "announcement");
        return saved;
    }

    public void deleteAnnouncement(Long id) {
        GeneralAnnouncement a = generalAnnouncementRepository.findById(id).orElseThrow();
        a.setIsDeleted(1);
        generalAnnouncementRepository.save(a);
    }

    public GeneralAnnouncement toggleAnnouncementActive(Long id) {
        GeneralAnnouncement a = generalAnnouncementRepository.findById(id).orElseThrow();
        boolean newActive = !Boolean.TRUE.equals(a.getActive());
        a.setActive(newActive);
        if (newActive) {
            a.setActivatedAt(LocalDateTime.now());
        }
        return generalAnnouncementRepository.save(a);
    }

    public List<DonationCause> getDonationCauses(Long masjidId) {
        return donationCauseRepository.findByMasjidIdAndIsDeleted(masjidId, 0);
    }

    public DonationCause saveDonationCause(Long masjidId, DonationCause dc) {
        dc.setMasjid(getMasjid(masjidId));
        return donationCauseRepository.save(dc);
    }

    public void deleteDonationCause(Long id) {
        DonationCause dc = donationCauseRepository.findById(id).orElseThrow();
        dc.setIsDeleted(1);
        donationCauseRepository.save(dc);
    }

    public List<MonthlyDonation> getMonthlyDonations(Long masjidId) {
        return monthlyDonationRepository.findByMasjidIdAndIsDeleted(masjidId, 0);
    }

    public MonthlyDonation saveMonthlyDonation(Long masjidId, MonthlyDonation md) {
        md.setMasjid(getMasjid(masjidId));
        return monthlyDonationRepository.save(md);
    }

    public void deleteMonthlyDonation(Long id) {
        MonthlyDonation md = monthlyDonationRepository.findById(id).orElseThrow();
        md.setIsDeleted(1);
        monthlyDonationRepository.save(md);
    }

    public List<Expense> getExpenses(Long masjidId) {
        return expenseRepository.findByMasjidIdAndIsDeleted(masjidId, 0);
    }

    public Expense saveExpense(Long masjidId, Expense expense) {
        expense.setMasjid(getMasjid(masjidId));
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        Expense e = expenseRepository.findById(id).orElseThrow();
        e.setIsDeleted(1);
        expenseRepository.save(e);
    }

    public List<AboutService> getServices(Long masjidId) {
        return aboutServiceRepository.findByMasjidIdAndIsDeleted(masjidId, 0);
    }

    public AboutService saveService(Long masjidId, AboutService service) {
        service.setMasjid(getMasjid(masjidId));
        return aboutServiceRepository.save(service);
    }

    public void deleteService(Long id) {
        AboutService s = aboutServiceRepository.findById(id).orElseThrow();
        s.setIsDeleted(1);
        aboutServiceRepository.save(s);
    }

    public List<TeamMember> getTeamMembers(Long masjidId) {
        return teamMemberRepository.findByMasjidIdAndIsDeleted(masjidId, 0);
    }

    public TeamMember saveTeamMember(Long masjidId, TeamMember tm) {
        tm.setMasjid(getMasjid(masjidId));
        return teamMemberRepository.save(tm);
    }

    public void deleteTeamMember(Long id) {
        TeamMember tm = teamMemberRepository.findById(id).orElseThrow();
        tm.setIsDeleted(1);
        teamMemberRepository.save(tm);
    }
}
