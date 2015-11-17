package org.achitocca.rest.resource;


import java.util.logging.Logger;

import org.achitocca.business.AChiToccaManager;
import org.achitocca.dto.MessageDto;
import org.restlet.representation.Representation;
import org.restlet.resource.*; 
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.ext.json.*;

public class aChiToccaResource<K>  extends ServerResource {
	private static final Logger log = Logger.getLogger(aChiToccaResource.class.getName());
	
	
		/*Get the next id for the user given a group* 
		 */
		@Get("json")
		public Representation aChiTocca(){
			//create json response
			JsonRepresentation representation = null;
			try {
				log.info("start GET aChiTocca() of aChiToccaResource");
				
				 for (Parameter parameter : this.getRequest().getResourceRef().getQueryAsForm()) {
			        	log.info("parameter " + parameter.getName());
			   		  	log.info("/" + parameter.getValue());
			        }	
			    String group_id= this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("p_group_id");;
			    String fb_token=this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("p_fb_token");
				String external_user_id = this.getRequest().getResourceRef().getQueryAsForm().getFirstValue("p_external_user_id");
			    //****Manage request				
				String nextUserid = AChiToccaManager.aChiTocca(group_id, external_user_id, fb_token);
				
				//***Manage result
				
				
				MessageDto 	msgDto = new MessageDto();
				msgDto.result="aChiTocca completed!";
				msgDto.code=0;
				msgDto.detail=nextUserid;
						
				
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
				log.info("end GET aChiTocca() of aChiToccaResource");
				
			}
			
			
		}
	
}
