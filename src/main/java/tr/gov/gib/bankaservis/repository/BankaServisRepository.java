package tr.gov.gib.bankaservis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.gov.gib.bankaservis.entity.BankaServis;

@Repository
public interface BankaServisRepository extends JpaRepository<BankaServis, Integer> {
    BankaServis findByKrediKartiAndKartSahibiAndCcvAndSkAyAndSkYil(String krediKarti, String kartSahibi, Short ccv, Short skAy, Short skYil);
}