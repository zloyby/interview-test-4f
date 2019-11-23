package by.zloy.db.browser.zeaver.service;

import by.zloy.db.browser.zeaver.controller.response.ConnectionResponse;
import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.repository.ConnectionRepository;
import by.zloy.db.browser.zeaver.util.ModelMapperUtils;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @PostConstruct
    public void init() {
        log.trace("Init:[{}]", this.getClass().getSimpleName());
    }

    @Cacheable("Connections")
    @Transactional(readOnly = true)
    public Page<ConnectionResponse> getAllConnections(PageRequest pageRequest) {
        final Page<Connection> connections = connectionRepository.findAll(pageRequest);
        return ModelMapperUtils.mapAllPages(connections, ConnectionResponse.class);
    }
}
