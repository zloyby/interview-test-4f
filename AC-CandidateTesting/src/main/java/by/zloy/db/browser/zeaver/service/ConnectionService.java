package by.zloy.db.browser.zeaver.service;

import by.zloy.db.browser.zeaver.controller.request.ConnectionRequest;
import by.zloy.db.browser.zeaver.exception.NotFoundException;
import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.model.Driver;
import by.zloy.db.browser.zeaver.repository.ConnectionRepository;
import by.zloy.db.browser.zeaver.service.dbcp.DataSourceBeanFactory;
import by.zloy.db.browser.zeaver.util.ModelMapperUtils;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final DataSourceBeanFactory dataSourceBeanFactory;

    @Autowired
    public ConnectionService(ConnectionRepository connectionRepository,
                             DataSourceBeanFactory dataSourceBeanFactory) {
        this.connectionRepository = connectionRepository;
        this.dataSourceBeanFactory = dataSourceBeanFactory;
    }

    @PostConstruct
    public void init() {
        log.trace("Init:[{}]", this.getClass().getSimpleName());
    }

    public Connection createConnection(ConnectionRequest connectionRequest) {
        log.info("create Connection {}", connectionRequest);

        final Connection mapped = ModelMapperUtils.map(connectionRequest, Connection.class);
        final Connection saved = connectionRepository.save(mapped);

        dataSourceBeanFactory.addIfNotExist(saved.getId(), saved);

        return saved;
    }

    @Transactional(readOnly = true)
    public Page<Connection> getAllConnections(Pageable pageable) {
        log.info("get all Connections {}-{}", pageable.getPageNumber(), pageable.getPageSize());

        return connectionRepository.findAll(pageable);
    }

    @Cacheable(value = "connections", key = "#id")
    @Transactional(readOnly = true)
    public Connection getConnection(Long id) {
        log.info("get Connection {}", id);

        return connectionRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @CachePut(value = "connections", key = "#id")
    public Connection updateConnection(Long id, ConnectionRequest connectionRequest) {
        log.info("update Connection {}", id);

        final Connection found = connectionRepository.findById(id).orElseThrow(NotFoundException::new);

        dataSourceBeanFactory.deleteIfExist(id);

        found.setName(connectionRequest.getName());
        found.setDriver(Driver.valueOf(connectionRequest.getDriver()));
        found.setDatabase(connectionRequest.getDatabase());
        found.setHost(connectionRequest.getHost());
        found.setPort(connectionRequest.getPort());
        found.setUser(connectionRequest.getUser());
        found.setPassword(connectionRequest.getPassword());
        found.setParameters(connectionRequest.getParameters());
        final Connection saved = connectionRepository.save(found);

        dataSourceBeanFactory.addIfNotExist(saved.getId(), saved);

        return saved;
    }

    @CacheEvict(value = "connections", key = "#id")
    public void deleteConnection(Long id) {
        log.info("delete Connection {}", id);

        dataSourceBeanFactory.deleteIfExist(id);
        connectionRepository.deleteById(id);
    }
}
