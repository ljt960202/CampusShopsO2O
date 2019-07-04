package com.ljt.o2o.dao.spilt;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;
@Intercepts({@Signature(type= Executor.class,method="update",args= {MappedStatement.class,Object.class}),
	@Signature(type= Executor.class,method="query",args={MappedStatement.class,Object.class,
				RowBounds.class,ResultHandler.class})})
public class DynamicDateSourceInterceptor implements Interceptor{
	private static Logger logger = LoggerFactory.getLogger(DynamicDateSourceInterceptor.class);
	
	private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		//获取增删改查操作的参数
		Object[] objects = invocation.getArgs();
		//获取是什么方法，第一个参数一般是方法类型
		MappedStatement ms = (MappedStatement)objects[0];
		String lookupKey = DynamicDataSourceHolder.DB_MASTER;
		if(synchronizationActive!=true) {
			//读方法
			if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				//selectKey为自增id查询主键(select last_insert_id())方法,使用主库
				if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				}else {
					BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
					String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("[\\t\\n\\r]", " ");
					if(sql.matches(REGEX)) {
						lookupKey = DynamicDataSourceHolder.DB_MASTER;
					}else {
						lookupKey = DynamicDataSourceHolder.DB_SLAVE;
					}
				}
			}
		}else {
			lookupKey = DynamicDataSourceHolder.DB_MASTER;
		}
		logger.debug("设置方法[{}] use[{}] Strategy,SqlCommanType[{}]..",ms.getId(),lookupKey,ms.getSqlCommandType().name());
		DynamicDataSourceHolder.setDbType(lookupKey);
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		//Executor 增删改查操作
		if(target instanceof Executor) {
			return Plugin.wrap(target, this);
		}else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

}
