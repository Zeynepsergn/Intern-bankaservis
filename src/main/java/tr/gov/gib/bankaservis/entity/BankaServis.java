package tr.gov.gib.bankaservis.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "banka_servis", schema = "gsths_banka")
public class BankaServis {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banka_servis_id_gen")
    @SequenceGenerator(name = "banka_servis_id_gen", sequenceName = "banka_servis_kullanıcı_id_seq", allocationSize = 1)
    @Column(name = "\"kullanıcı_id\"", nullable = false)
    private Integer id;

    @Size(max = 16)
    @Column(name = "kredi_karti", length = 16)
    private String krediKarti;

    @NotNull
    @Column(name = "kart_sahibi", nullable = false, length = Integer.MAX_VALUE)
    private String kartSahibi;

    @Column(name = "ccv")
    private Short ccv;

    @Column(name = "sk_ay")
    private Short skAy;

    @Column(name = "\"sk_yİl\"")
    private Short skYil;

    @Column(name = "bakiye", precision = 10, scale = 2)
    private BigDecimal bakiye;

    @Column(name = "banka_adi", length = Integer.MAX_VALUE)
    private String bankaAdi;

}