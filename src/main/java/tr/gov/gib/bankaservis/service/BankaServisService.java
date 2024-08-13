package tr.gov.gib.bankaservis.service;

import tr.gov.gib.bankaservis.object.request.BankaServerRequest;
import tr.gov.gib.bankaservis.object.response.BankaServerResponse;

public interface BankaServisService {
    BankaServerResponse odemeYap(BankaServerRequest request);
}