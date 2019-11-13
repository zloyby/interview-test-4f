package cz.finance.hr.test.core.service;

import cz.finance.hr.test.api.model.City;
import cz.finance.hr.test.api.model.IpAddressRange;
import cz.finance.hr.test.api.service.IPAddressService;
import java.net.InetAddress;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class IPAddressServiceImpl implements IPAddressService {

    @Autowired
    private ModelMapper modelMapper;

    @Nonnull
    @Override
    public List<IpAddressRange> getAllCityIPAddressRanges(@Nonnull Long cityId) {
        //TODO implement me!
        return null;
    }

    @Nullable
    @Override
    public City guessCityForIPAddress(@Nonnull InetAddress ipAddress) {
        //TODO implement me!
        return null;
    }

}
