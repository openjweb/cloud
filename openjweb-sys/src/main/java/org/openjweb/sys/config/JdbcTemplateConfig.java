package org.openjweb.sys.config;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
//@AutoConfigureBefore({ShiroConfig.class})
public class JdbcTemplateConfig {
    private static JdbcTemplate  defaultJdbcTemplate  = null;

    public static JdbcTemplate  getDefaultJdbcTemplate()
    {
        return defaultJdbcTemplate;
    }

    @Bean(name = "jdbcTemplateOne")
    JdbcTemplate jdbcTemplateOne(@Qualifier("dsOne") DataSource dataSource) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        this.defaultJdbcTemplate = jdbcTemplate;
        return jdbcTemplate;
        //return new JdbcTemplate(dataSource);
    }

    @Bean(name = "jdbcTemplateTwo")
    JdbcTemplate jdbcTemplateTwo(@Qualifier("dsTwo") DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

    //@Bean
    //JdbcTemplate jdbcTemplate3(@Qualifier("ds3") DataSource dataSource) {
    //	return new JdbcTemplate(dataSource);
    //}

}