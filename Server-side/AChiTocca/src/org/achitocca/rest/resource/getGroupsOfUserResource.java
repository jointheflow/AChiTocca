package org.achitocca.rest.resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.achitocca.business.AChiToccaManager;
import org.achitocca.business.model.Group;
import org.achitocca.dto.GroupDto;
import org.achitocca.dto.MessageDto;
import org.restlet.representation.Representation;
import org.restlet.resource.*; 
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.ext.json.*;

public class getGroupsOfUserResource<K>  extends ServerResource {
	private static final Logger log = Logger.getLogger(getGroupsOfUserResource.class.getName());
	
	
		/*Get all groups of the user* 
		 */
		@Get("json")
		public Representation getGroupsOfUser(){
			//create json response
			JsonRepresentation representation = null;
			try {
				log.info("start GET getGroupsOfUser() of getGroupsOfUserResource");
				
				 for (Parameter parameter : this.getRequest().getResourceRef().getQueryAsForm()) {
			        	log.info("parameter " + parameter.getName());
			   		  	log.info("/" + parameter.getValue());
			        }	
			    String external_user_id= this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("p_external_user_id");;
			    String fb_token=this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("p_fb_token");;
				
			    //****Manage request				
				ArrayList<Group> groupList = AChiToccaManager.getGroupsOfUser(external_user_id, fb_token);
				
				//***Manage result
				ArrayList<GroupDto> dtoGroupList = new ArrayList<GroupDto>();
				Iterator<Group> grpItr = groupList.iterator();
				while (grpItr.hasNext()) {
					Group g = grpItr.next();
					GroupDto gDto = new GroupDto();
					gDto.externalGroupId= g.getExternalGroupId();
					gDto.groupId = g.getGroupId();
					gDto.nextUser = g.getNextUser();
					gDto.turn = g.getTurn();
					
					dtoGroupList.add(gDto);
				}
				
				MessageDto 	msgDto = new MessageDto();
				msgDto.result="getGroupsOfUser completed!";
				msgDto.code=0;
				msgDto.detail=dtoGroupList;
						
				
				representation= new JsonRepresentation(msgDto);
				representation.setIndenting(true);
				
				return representation;
				
			}/*catch (FacebookOAuthException e_oa) {
				e_oa.printStackTrace();
				setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				ErrorResource error = new ErrorResource();
				error.setErrorCode(ErrorResource.WRONG_OAUTH_TOKEN);
				error.setErrorMessage(e_oa.getMessage());
				JsonRepresentation errorRepresentation = new JsonRepresentation(error);
				return errorRepresentation;
				
			}catch (PlatformNotManagedException e_pnm) {
				e_pnm.printStackTrace();
				setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				ErrorResource error = new ErrorResource();
				error.setErrorCode(ErrorResource.PLATFORM_NOT_MANAGED_ERROR);
				error.setErrorMessage(e_pnm.getMessage());
				JsonRepresentation errorRepresentation = new JsonRepresentation(error);
				return errorRepresentation;
			
			}*/catch (NumberFormatException e_ne) {
				e_ne.printStackTrace();
				setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				ErrorResource error = new ErrorResource();
				error.setErrorCode(ErrorResource.NUMBER_FORMAT_ERROR);
				error.setErrorMessage(e_ne.getMessage());
				JsonRepresentation errorRepresentation = new JsonRepresentation(error);
				return errorRepresentation;
				
			}/*catch (WrongParameterException e_wp) {
				e_wp.printStackTrace();
				setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				ErrorResource error = new ErrorResource();
				error.setErrorCode(ErrorResource.WRONG_PARAMETER_ERROR);
				error.setErrorMessage(e_wp.getMessage());
				JsonRepresentation errorRepresentation = new JsonRepresentation(error);
				return errorRepresentation;				
			}*/catch (Exception e) {
				e.printStackTrace();
				setStatus(Status.SERVER_ERROR_INTERNAL);
				ErrorResource error = new ErrorResource();
				error.setErrorCode(ErrorResource.INTERNAL_SERVER_ERROR);
				error.setErrorMessage(e.getMessage());
				
				JsonRepresentation errorRepresentation = new JsonRepresentation(error);
				return errorRepresentation;
				
			}
			
			finally {
				log.info("end GET getGroupsOfUser() of getGroupsOfUserResource");
				
			}
			
			
		}
	
}
