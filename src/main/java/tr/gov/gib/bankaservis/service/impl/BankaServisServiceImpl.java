package tr.gov.gib.bankaservis.service.impl;

import org.springframework.stereotype.Service;
import tr.gov.gib.bankaservis.entity.BankaServis;
import tr.gov.gib.bankaservis.object.request.BankaServerRequest;
import tr.gov.gib.bankaservis.object.response.BankaServerResponse;
import tr.gov.gib.bankaservis.repository.BankaServisRepository;
import tr.gov.gib.bankaservis.service.BankaServisService;
import tr.gov.gib.bankaservis.util.HashUtil;
import org.springframework.beans.factory.annotation.Value;

@Service
public class BankaServisServiceImpl implements BankaServisService {

    private final BankaServisRepository bankaServisRepository;

    public BankaServisServiceImpl(BankaServisRepository bankaServisRepository) {
        this.bankaServisRepository = bankaServisRepository;
    }

    @Value("${banka.salt}")
    private String salt;

    @Override
    public BankaServerResponse odemeYap(BankaServerRequest request) {
        //verilerin kontrol repo

        BankaServis bankaServisEntity = bankaServisRepository.findByKrediKartiAndKartSahibiAndCcvAndSkAyAndSkYil(
                request.getKartNo(), request.getKartSahibi(), request.getCcv().shortValue(),
                request.getSonKullanimTarihiAy().shortValue(), request.getSonKullanimTarihiYil().shortValue());
        System.out.println("Request: " + request);
        String generatedHash = HashUtil.generateSHA256(request.getOid(), request.getKartNo(), request.getOdenecekTutar().toString(),salt);
        System.out.println("Hash: " + generatedHash);

        if (bankaServisEntity == null) {
            return BankaServerResponse.builder()
                    .message("Kart bilgileri hatalı")
                    .oid(request.getOid())
                    .status("FAILURE")
                    .hash(generatedHash)
                    .build();
        }

        if (bankaServisEntity.getBakiye().compareTo(request.getOdenecekTutar()) < 0) {
            return BankaServerResponse.builder()
                    .bankaAdi(bankaServisEntity.getBankaAdi())
                    .message("Yetersiz bakiye")
                    .oid(request.getOid())
                    .status("FAILURE")
                    .hash(generatedHash)
                    .build();
        }

        bankaServisEntity.setBakiye(bankaServisEntity.getBakiye().subtract(request.getOdenecekTutar()));
        bankaServisRepository.save(bankaServisEntity);

        return BankaServerResponse.builder()
                .bankaAdi(bankaServisEntity.getBankaAdi())
                .message("Ödeme başarılı")
                .oid(request.getOid())
                .posId(bankaServisEntity.getId())
                .status("SUCCESS")
                .hash(generatedHash)
                .build();
    }
}