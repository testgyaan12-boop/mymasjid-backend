package com.noormasjid.service;

import com.noormasjid.entity.cms.*;
import com.noormasjid.entity.masjid.Masjid;
import com.noormasjid.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CmsService {

    private final BrandingRepository brandingRepository;
    private final HomeAnnouncementRepository homeAnnouncementRepository;
    private final PrayerTimeRepository prayerTimeRepository;
    private final JumuahConfigRepository jumuahConfigRepository;
    private final RamadanConfigRepository ramadanConfigRepository;
    private final JanazahRepository janazahRepository;
    private final GumshudaRepository gumshudaRepository;
    private final GeneralAnnouncementRepository generalAnnouncementRepository;
    private final DonationCauseRepository donationCauseRepository;
    private final MonthlyDonationRepository monthlyDonationRepository;
    private final ExpenseRepository expenseRepository;
    private final AboutServiceRepository aboutServiceRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MasjidRepository masjidRepository;

    public CmsService(BrandingRepository brandingRepository,
                      HomeAnnouncementRepository homeAnnouncementRepository,
                      PrayerTimeRepository prayerTimeRepository,
                      JumuahConfigRepository jumuahConfigRepository,
                      RamadanConfigRepository ramadanConfigRepository,
                      JanazahRepository janazahRepository,
                      GumshudaRepository gumshudaRepository,
                      GeneralAnnouncementRepository generalAnnouncementRepository,
                      DonationCauseRepository donationCauseRepository,
                      MonthlyDonationRepository monthlyDonationRepository,
                      ExpenseRepository expenseRepository,
                      AboutServiceRepository aboutServiceRepository,
                      TeamMemberRepository teamMemberRepository,
                      MasjidRepository masjidRepository) {
        this.brandingRepository = brandingRepository;
        this.homeAnnouncementRepository = homeAnnouncementRepository;
        this.prayerTimeRepository = prayerTimeRepository;
        this.jumuahConfigRepository = jumuahConfigRepository;
        this.ramadanConfigRepository = ramadanConfigRepository;
        this.janazahRepository = janazahRepository;
        this.gumshudaRepository = gumshudaRepository;
        this.generalAnnouncementRepository = generalAnnouncementRepository;
        this.donationCauseRepository = donationCauseRepository;
        this.monthlyDonationRepository = monthlyDonationRepository;
        this.expenseRepository = expenseRepository;
        this.aboutServiceRepository = aboutServiceRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.masjidRepository = masjidRepository;
    }

    private Masjid getMasjid(Long masjidId) {
        return masjidRepository.findById(masjidId)
                .orElseThrow(() -> new IllegalArgumentException("Masjid not found"));
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
        prayerTimeRepository.deleteByMasjidId(masjidId);
        Masjid masjid = getMasjid(masjidId);
        for (int i = 0; i < times.size(); i++) {
            PrayerTime pt = times.get(i);
            pt.setId(null);
            pt.setMasjid(masjid);
            pt.setSortOrder(i);
        }
        return prayerTimeRepository.saveAll(times);
    }

    public JumuahConfig getJumuah(Long masjidId) {
        return jumuahConfigRepository.findByMasjidId(masjidId).orElse(null);
    }

    public JumuahConfig saveJumuah(Long masjidId, JumuahConfig config) {
        JumuahConfig existing = jumuahConfigRepository.findByMasjidId(masjidId).orElse(new JumuahConfig());
        existing.setMasjid(getMasjid(masjidId));
        existing.setPrayerTime(config.getPrayerTime());
        existing.setAzaanTime(config.getAzaanTime());
        return jumuahConfigRepository.save(existing);
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

    public List<Janazah> getJanazahs(Long masjidId) {
        return janazahRepository.findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(masjidId, 0);
    }

    public Janazah saveJanazah(Long masjidId, Janazah janazah) {
        janazah.setMasjid(getMasjid(masjidId));
        return janazahRepository.save(janazah);
    }

    public void deleteJanazah(Long id) {
        Janazah j = janazahRepository.findById(id).orElseThrow();
        j.setIsDeleted(1);
        janazahRepository.save(j);
    }

    public List<Gumshuda> getGumshudas(Long masjidId) {
        return gumshudaRepository.findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(masjidId, 0);
    }

    public Gumshuda saveGumshuda(Long masjidId, Gumshuda gumshuda) {
        // Normalize contact and block duplicate live number globally (active=true && found=false)
        if (gumshuda.getContact() != null && !gumshuda.getContact().trim().isEmpty()) {
            String norm = gumshuda.getContact().replaceAll("[\\s\\-()]", "").trim();
            // Keep +91 etc, just strip spaces/dashes/()
            // Check global live duplicates (masjid wise nahi, all users see alerts)
            java.util.List<Gumshuda> live = gumshudaRepository.findByIsDeletedAndActiveAndFound(0, true, false);
            for (Gumshuda g : live) {
                if (g.getContact() == null) continue;
                String existingNorm = g.getContact().replaceAll("[\\s\\-()]", "").trim();
                if (existingNorm.equals(norm)) {
                    throw new IllegalArgumentException("Duplicate live contact: " + gumshuda.getContact() + " already has an active alert");
                }
            }
            gumshuda.setContact(norm);
        }
        gumshuda.setMasjid(getMasjid(masjidId));
        return gumshudaRepository.save(gumshuda);
    }

    public void deleteGumshuda(Long id) {
        Gumshuda g = gumshudaRepository.findById(id).orElseThrow();
        g.setIsDeleted(1);
        gumshudaRepository.save(g);
    }

    public List<GeneralAnnouncement> getAnnouncements(Long masjidId) {
        return generalAnnouncementRepository.findByMasjidIdAndIsDeletedOrderByCreatedAtDesc(masjidId, 0);
    }

    public GeneralAnnouncement saveAnnouncement(Long masjidId, GeneralAnnouncement a) {
        a.setMasjid(getMasjid(masjidId));
        return generalAnnouncementRepository.save(a);
    }

    public void deleteAnnouncement(Long id) {
        GeneralAnnouncement a = generalAnnouncementRepository.findById(id).orElseThrow();
        a.setIsDeleted(1);
        generalAnnouncementRepository.save(a);
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
