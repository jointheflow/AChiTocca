package org.achitocca.dto;

import java.util.ArrayList;
import java.util.Iterator;

import org.achitocca.business.model.Group;
import org.achitocca.business.model.User;

public class FactoryDto {
	
	public static GroupDto groupDtoFromGroup(Group group) {
		GroupDto groupDto = new GroupDto();
		groupDto.setExternalGroupId(group.getExternalGroupId());
		groupDto.setGroupId(group.getGroupId());
		groupDto.setExternalAdminId(group.getAdmin().getExternalUserId());
		groupDto.setTurn(group.getTurn());
		ArrayList<String> usersDto = new ArrayList<String>();
		Iterator<User> usrItr = group.getUsers().iterator();
		while (usrItr.hasNext()) {
			usersDto.add(usrItr.next().getExternalUserId());
			
		}
		groupDto.setUsersId(usersDto);
		
		return groupDto;
		
	}
}
