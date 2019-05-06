package com.gn4me.app.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gn4me.app.core.dao.IGeneralDao;
import com.gn4me.app.entities.Section;
import com.gn4me.app.entities.Status;
import com.gn4me.app.entities.SystemConfiguration;
import com.gn4me.app.entities.Status;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.log.LogHelper;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SystemLoader implements SchedulingConfigurer{

	@Autowired
	IGeneralDao generalDao;

	@Autowired
	private LogHelper logHelper;

	public static Map<Integer, Status> statusPerId;
	public static Map<String, Status> statusPerCode;
	public static Map<String, String> systemConfigurations;
	public static Map<String, Section> systemSections;

	@Scheduled(fixedRate = 3600000)
	@PostConstruct
	public void getStatusPerId() throws Exception {

		Transition transition = new Transition();

		try {
			logHelper.log("Going to Load System Status Per ID", transition);

			List<Status> statusList = generalDao.listSytemStatus(null, transition);
			statusPerId = new HashMap<Integer, Status>();
			for (Status status : statusList)
				statusPerId.put(status.getId(), status);

			logHelper.log("System Status Per ID Loaded as: " + statusPerId, transition);
		} catch (Exception exp) {
			logHelper.logExp(exp, " Get System Status Per ID ", transition);
		}
	}

	@Scheduled(fixedRate = 3700000)
	@PostConstruct
	public void getStatusPerCode() throws Exception {

		Transition transition = new Transition();

		try {
			logHelper.log("Going to Load System Status Per Code", transition);

			List<Status> statusList = generalDao.listSytemStatus(null, new Transition());
			statusPerCode = new HashMap<String, Status>();
			for (Status status : statusList)
				statusPerCode.put(status.getCode(), status);

			logHelper.log("System Status Per ID Loaded as: " + statusPerCode, transition);
		} catch (Exception exp) {
			logHelper.logExp(exp, " Get System Status Per Code ", transition);
		}
	}

	@Scheduled(fixedRate = 3700000)
	@PostConstruct
	public void getSystemConfigurations() throws Exception {

		Transition transition = new Transition();

		try {
			logHelper.log("Going to Load System configurations ", transition);

			List<SystemConfiguration> configurationsList = generalDao.listSytemConfigurations(new Transition());
			systemConfigurations = new HashMap<String, String>();
			for (SystemConfiguration configuration : configurationsList)
				systemConfigurations.put(configuration.getKey(), configuration.getValue());

			logHelper.log("System configurations Loaded as: " + systemConfigurations, transition);
		} catch (Exception exp) {
			logHelper.logExp(exp, " Get System configurations ", transition);
		}
	}

	@Scheduled(fixedRate = 3700000)
	@PostConstruct
	public void getSystemSections() throws Exception {

		Transition transition = new Transition();

		try {
			logHelper.log("Going to Load System sections configurations ", transition);

			List<Section> sectionsList = generalDao.listSections(new Transition());
			systemSections = new HashMap<String, Section>();
			for (Section section : sectionsList)
				systemSections.put(section.getSectionCode(), section);

			logHelper.log("System sections configurations Loaded as: " + systemSections, transition);
		} catch (Exception exp) {
			logHelper.logExp(exp, " Get System sections configurations ", transition);
		}
	}

	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (Objects.equals(value, entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	@Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdownNow")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

}
