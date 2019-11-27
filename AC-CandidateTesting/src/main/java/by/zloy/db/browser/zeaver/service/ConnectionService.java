package by.zloy.db.browser.zeaver.service;

import by.zloy.db.browser.zeaver.controller.request.ConnectionRequest;
import by.zloy.db.browser.zeaver.controller.response.ConnectionResponse;
import by.zloy.db.browser.zeaver.exception.NotFoundException;
import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.repository.ConnectionRepository;
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

    @Autowired
    public ConnectionService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @PostConstruct
    public void init() {
        log.trace("Init:[{}]", this.getClass().getSimpleName());
    }

    //TODO: add cache with pagination
    @Transactional(readOnly = true)
    public Page<ConnectionResponse> getAllConnections(Pageable pageable) {
        log.info("get all Connections {}-{}", pageable.getPageNumber(), pageable.getPageSize());
        final Page<Connection> connections = connectionRepository.findAll(pageable);

        log.info("found {}", connections.getTotalElements());
        return ModelMapperUtils.mapAllPages(connections, ConnectionResponse.class);
    }

    public ConnectionResponse createConnection(ConnectionRequest connectionRequest) {
        log.info("create Connection {}", connectionRequest);
        final Connection connection = ModelMapperUtils.map(connectionRequest, Connection.class);

        final Connection saved = connectionRepository.save(connection);
        log.info("saved Connection {}", saved);

        return ModelMapperUtils.map(saved, ConnectionResponse.class);
    }

    @Cacheable(value = "connections", key = "#id")
    @Transactional(readOnly = true)
    public ConnectionResponse getConnection(Long id) {
        log.info("get Connection {}", id);
        final Connection connection = connectionRepository.findById(id).orElseThrow(NotFoundException::new);

        log.info("founded Connection {}", connection);
        return ModelMapperUtils.map(connection, ConnectionResponse.class);
    }

    @CachePut(value = "connections", key = "#id")
    public ConnectionResponse updateConnection(Long id, ConnectionRequest connectionRequest) {
        log.info("update Connection {}", id);
        final Connection connection = connectionRepository.findById(id).orElseThrow(NotFoundException::new);

        connection.setName(connectionRequest.getName());
        connection.setDatabase(connectionRequest.getDatabase());
        connection.setHost(connectionRequest.getHost());
        connection.setPort(connectionRequest.getPort());
        connection.setUser(connectionRequest.getUser());
        connection.setPassword(connectionRequest.getPassword());
        final Connection saved = connectionRepository.save(connection);
        log.info("updated Connection {}", saved);

        return ModelMapperUtils.map(saved, ConnectionResponse.class);
    }

    @CacheEvict(value = "connections", key = "#id")
    public void deleteConnection(Long id) {
        log.info("delete Connection {}", id);
        connectionRepository.deleteById(id);
    }
}
