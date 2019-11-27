package by.zloy.db.browser.zeaver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceBeanFactory {

    private Map<Object, Object> dataSources;

}
