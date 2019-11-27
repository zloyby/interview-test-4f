package by.zloy.db.browser.zeaver.dbcp;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceBeanFactory {

    private Map<Object, Object> dataSources;

}
