package com.display.backoffice.sym.log.service;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;
import org.egovframe.rte.fdl.cmmn.exception.manager.AbstractExceptionHandleManager;
import org.egovframe.rte.fdl.cmmn.exception.manager.ExceptionHandlerService;
import com.display.backoffice.sym.log.vo.SysLog;

@Aspect
@Component
public class SysLogAspect   extends AbstractExceptionHandleManager implements ExceptionHandlerService {
	
	@Autowired
	private EgovSysLogService sysLogService;
 
	private static final Logger LOGGER = LoggerFactory.getLogger(SysLogAspect.class);
	public static final String KEY_ECODE = "ecode";
	
	/*@Before("execution(public * egovframework.let..web.Controller*(..))   or execution(public * com.display.backoffice..web.*Controller.*(..)))")
	public void before(JoinPoint joinPoint) throws Exception {
		LOGGER.info(" before [" + joinPoint.getTarget().getClass().getSimpleName() + "] ---------------------------------------------------------------------------------//");
		for (Object arg : joinPoint.getArgs()) {
			if (arg instanceof Map) {
				LOGGER.info(" (  before " + joinPoint.getSignature().getName() + ") Controller Parameters: " + arg);
			}
		}
	} */
	/*@AfterReturning(pointcut="execution(!void egovframework.let..web.*Controller.*(..))  || execution(!void com.display.backoffice..web.*Controller.*(..)) "
			+ "           && !@annotation(com.display.backoffice.sym.log.annotation.NoLogging)"
			+ "           && !@target(com.display.backoffice.sym.log.annotation.NoLogging)", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		LOGGER.debug("afterReturning----------------------------------------------------------------------------------------------------------");
		
		if (result instanceof ModelAndView) {
			ModelAndView mav = ((ModelAndView) result);
			if (!mav.getModel().isEmpty()) {
				LOGGER.info(" ( @AfterReturning 2 " + joinPoint.getSignature().getName() + ") Controller Return: " + mav.getModel());
			}
			if (mav.getModel().get(SysLogAspect.KEY_ECODE) == null) {
				mav.addObject(SysLogAspect.KEY_ECODE, 0);
			}
		}
		LOGGER.info(" [ @AfterReturning 3" + joinPoint.getTarget().getClass().getSimpleName() + "] ---------------------------------------------------------------------------------//");
	}*/
	
	public Object logSql(ProceedingJoinPoint joinPoint) throws Throwable {
		LOGGER.debug("SqlSession----------------------------------------------------------------------------------------------------------");
		Object[] methodArgs = joinPoint.getArgs(), sqlArgs = null;
		Object retValue = joinPoint.proceed();
		String statement = null;
		String sqlid = methodArgs[0].toString();
		
		
		LOGGER.debug("sqlid:" + sqlid);
		LOGGER.debug("length:" + methodArgs.length);

		for (int i =1, n = methodArgs.length; i < n; i++){
			Object arg = methodArgs[i];
			
			LOGGER.debug("methodArgs:" + methodArgs[i].toString());
			
			if (arg instanceof HashMap){
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>)arg;
				
				statement = ((SqlSessionTemplate)joinPoint.getTarget()).getConfiguration().getMappedStatement(sqlid).getBoundSql(map).getSql();
				
				sqlArgs = new Object[map.size()];
				Iterator<String> itr = map.keySet().iterator();
				
				int j = 0;
				while(itr.hasNext()){
					sqlArgs[j++] = map.get(itr.next());
				}
				
			}
			break;
		}
		String completedStatemane = (sqlArgs == null ? statement:fillParameters(statement, sqlArgs));
		LOGGER.debug("completedStatemane:" + completedStatemane);
		return retValue;
	}
	
	/**
	 * 시스템 로그정보를 생성한다.
	 * sevice Class의 update로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	public Object logUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
 
		SysLog sysLog = new SysLog();
 		Object sqlid  = null; 		
 		StopWatch stopWatch = new StopWatch();
 		 
		
		try {
			stopWatch.start();
			Object[] methodArgs = joinPoint.getArgs(), sqlArgs = null;
			if (methodArgs.length > 0){
				sqlid = methodArgs[0];
			}
			Object retValue = joinPoint.proceed();
			return retValue;
		} catch (NullPointerException e) {
			throw e;
		}  catch (Throwable e) {
			throw e;
		} finally {
			stopWatch.stop();
 
			sysLog.setSqlParam(ParamToJson.paramToJson(sqlid));
			String processSeCode =  ParamToJson.JsonKeyToString(sqlid, "mode").equals("Ins") ? "I" : "U";
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";
			String taskCount = Integer.toString(stopWatch.getTaskCount());
			/*
	        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	        
	    	if(isAuthenticated.booleanValue()) {
	    		AdminLoginVO user = (AdminLoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
				uniqId = user.getAdminId();// .getUniqId();
				ip = user.getIp() == null ? "": user.getIp();
	    	}
	    	*/
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			sysLog.setMethodResult("");
			
			
			
			sysLogService.logInsertSysLog(sysLog);
		
 
		}
 
	}
 
	/**
	 * 시스템 로그정보를 생성한다.
	 * sevice Class의 delete로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	public Object logDelete(ProceedingJoinPoint joinPoint) throws Throwable {
 
		StopWatch stopWatch = new StopWatch();
		SysLog sysLog = new SysLog();
 		Object sqlid  = null; 
 		
		try {
			stopWatch.start();
			Object[] methodArgs = joinPoint.getArgs(), sqlArgs = null;
			if (methodArgs.length > 0){
				sqlid = methodArgs[0];
			}
 
			Object retValue = joinPoint.proceed();
			return retValue;
		}  catch (NullPointerException e) {
			throw e;
		}catch (Throwable e) {
			throw e;
		} finally {
			
			stopWatch.stop();
			sysLog.setSqlParam(ParamToJson.paramToJson(sqlid));
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			String processSeCode = "D";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";
 
	    	/* Authenticated 
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();	        
	    	if(isAuthenticated.booleanValue()) {
	    		AdminLoginVO user = (AdminLoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
				uniqId = user.getAdminId();// .getUniqId();
				ip = user.getIp();
	    	}
 			 */
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			sysLogService.logInsertSysLog(sysLog);
 
		}
 
	}
	
	public void MapperSelect(ProceedingJoinPoint joinPoint) throws Throwable {
		LOGGER.debug("mapper--------------------------------------------------------------------------------------------------------------");
 		StopWatch stopWatch = new StopWatch();
 		Object sqlid  = null;
		try {
			stopWatch.start();
			Object[] methodArgs = joinPoint.getArgs(), sqlArgs = null;
			LOGGER.debug("length:" + methodArgs.length);
			for (Object  methodArg : methodArgs){
				System.out.println("methodArg:" + methodArg.toString());
			}
			stopWatch.stop();
			//return retValue;
		}catch (NullPointerException e) {
			throw e;
		} catch (Throwable e) {
			throw e;
		} finally {
			
			
			
			/*String queryID = ((MappedStatement)joinPoint.getArgs()[0]).getId();
			System.out.println("queryID:" + queryID);
			
	        //Query Parameter
			Object param = joinPoint.getArgs()[1];
			System.out.println("param:" + param);
			
			//Query String
	        String queryString = ((MappedStatement)joinPoint.getArgs()[0]).getBoundSql(param).getSql();
	        
	        System.out.println("queryString:" + queryString);*/
		}
		
	}
	/**
	 * 시스템 로그정보를 생성한다.
	 * sevice Class의 select로 시작되는 Method
	 *
	 * @param ProceedingJoinPoint
	 * @return Object
	 * @throws Exception
	 */
	//public Object logSelect(ProceedingJoinPoint joinPoint) throws Throwable {
	public void logSelect(JoinPoint joinPoint, Object result) throws Throwable {
		LOGGER.debug("logSelect--------------------------------------------------------------------------------------------------------------");
 		StopWatch stopWatch = new StopWatch();
 		SysLog sysLog = new SysLog();
 		Object sqlid  = null;
		try {
			stopWatch.start();
			Object[] methodArgs = joinPoint.getArgs(), sqlArgs = null;
			//LOGGER.debug("length:" + methodArgs.length);
			if (methodArgs.length > 0){
				sqlid = methodArgs[0];
			}
			stopWatch.stop();
			//return retValue;
		}catch (NullPointerException e) {
			throw e;
		}  catch (Throwable e) {
			throw e;
		} finally {
			
			sysLog.setSqlParam(ParamToJson.paramToJson(sqlid));
			sysLog.setMethodResult(ParamToJson.paramListToJson(result) );
			
			if (result instanceof ModelAndView  && result != null) {
				ModelAndView mav = ((ModelAndView) result);
				if (!mav.getModel().isEmpty()) {
					LOGGER.info(" ( @AfterReturning 2 " + joinPoint.getSignature().getName() + ") Controller Return: " + mav.getModel());
				}
				if (mav.getModel().get(SysLogAspect.KEY_ECODE) == null) {
					mav.addObject(SysLogAspect.KEY_ECODE, 0);
				}
			}
			
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			
			String processSeCode = "R";
			String processTime = Long.toString(stopWatch.getTotalTimeMillis());
			String uniqId = "";
			String ip = "";
			String taskCount = Integer.toString(stopWatch.getTaskCount());
 
	    	/* Authenticated 
	        Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	        //LOGGER.debug("isAuthenticated:" + isAuthenticated);
	        
	    	if(isAuthenticated.booleanValue()) {
	    		AdminLoginVO user = (AdminLoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
				uniqId = user.getAdminId();// .getUniqId();
				ip = user.getIp();
	    	}
	    	//ip = EgovClntInfo.getClntIP(request);
 			*/
			sysLog.setSrvcNm(className);
			sysLog.setMethodNm(methodName);
			sysLog.setProcessSeCode(processSeCode);
			sysLog.setProcessTime(processTime);
			sysLog.setRqesterId(uniqId);
			sysLog.setRqesterIp(ip);
			//
			
			
			//LOGGER.debug("className:" + className + ": methodName:" + methodName + ": taskCount:" + taskCount + ": uniqId:" + uniqId + " ip:" + ip );
			sysLogService.logInsertSysLog(sysLog);
 
		}
 
	}
	private String fillParameters(String statement, Object[] sqlArgs){
		StringBuilder completedSqlBuilder = new StringBuilder(Math.round(statement.length() * 1.2f));
		int index, prevIndex = 0;
		
		for (Object arg: sqlArgs){
			index = statement.indexOf("?", prevIndex);
			if (index == -1)
				completedSqlBuilder.append(statement.substring(prevIndex, index));
			
			if(arg == null)
				completedSqlBuilder.append("NULL");
			else
				completedSqlBuilder.append(":"+ arg.toString());
			prevIndex = index + 1;
		}
		if (prevIndex != statement.length())
			completedSqlBuilder.append(statement.substring(prevIndex));
		
		return completedSqlBuilder.toString();
	}

	
}
