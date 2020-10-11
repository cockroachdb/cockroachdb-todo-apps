/*
 *    Copyright 2020 - Romain Coudour
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.cockroachdb.hacktoberfest.config;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.cockroachdb.hacktoberfest.model.jooq.db.public__.tables"})
@EnableTransactionManagement
public class PersistenceContext {

	@Autowired
	DataSource dataSource;

	@Bean
	public DataSourceConnectionProvider connectionProvider() {
		return new DataSourceConnectionProvider(
				new TransactionAwareDataSourceProxy(dataSource));
	}

	@Bean
	public DSLContext dsl() {
		return new DefaultDSLContext(configuration());
	}

	public DefaultConfiguration configuration() {
		final DefaultConfiguration config = new DefaultConfiguration();
		config.set(connectionProvider());
		config.set(SQLDialect.POSTGRES);
		config.set(new DefaultExecuteListenerProvider(
				new JooqExceptionTranslator()));
		return config;
	}
}
