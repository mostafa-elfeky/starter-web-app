package com.gn4me.app.core.dao;

import java.util.List;

import com.gn4me.app.entities.Gender;
import com.gn4me.app.entities.Section;
import com.gn4me.app.entities.SystemConfiguration;
import com.gn4me.app.entities.Status;
import com.gn4me.app.entities.Transition;
import com.gn4me.app.entities.enums.SystemModuleEnum;

public interface IGeneralDao {

	
	public List<Status> listSytemStatus(SystemModuleEnum module, Transition transition) throws Exception;
	
	public List<Gender> listGenders(Transition transition) throws Exception;
	
	public List<SystemConfiguration> listSytemConfigurations(Transition transition) throws Exception;
	
	public List<Section> listSections(Transition transition)throws Exception;
}
