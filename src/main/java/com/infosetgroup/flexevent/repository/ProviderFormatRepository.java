package com.infosetgroup.flexevent.repository;

import com.infosetgroup.flexevent.entity.Provider;
import com.infosetgroup.flexevent.entity.ProviderFormat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderFormatRepository extends JpaRepository<ProviderFormat, Long> {
    ProviderFormat findByProviderAndLengthAndHeader(Provider provider, int length, String header);
    ProviderFormat findByLengthAndHeader(int length, String header);
    ProviderFormat findByHeader(String header);
}
