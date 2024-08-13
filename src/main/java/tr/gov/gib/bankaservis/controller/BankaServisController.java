package tr.gov.gib.bankaservis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.gov.gib.bankaservis.object.request.BankaServerRequest;
import tr.gov.gib.bankaservis.object.response.BankaServerResponse;
import tr.gov.gib.bankaservis.service.BankaServisService;

@RestController
@RequestMapping("/api/banka")
public class BankaServisController {

    private final BankaServisService bankaServisService;

    public BankaServisController(BankaServisService bankaServisService) {
        this.bankaServisService = bankaServisService;
    }

    @PostMapping("/odeme")
    public ResponseEntity<BankaServerResponse> odemeYap(@RequestBody BankaServerRequest request) {
        BankaServerResponse response = bankaServisService.odemeYap(request);
        return ResponseEntity.ok(response);
    }
}