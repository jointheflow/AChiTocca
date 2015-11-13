package org.achitocca.rest.resource;



import java.util.logging.Logger;





import org.achitocca.business.AChiToccaManager;
import org.achitocca.dto.MessageDto;
import org.restlet.representation.Representation;
import org.restlet.resource.*; 
import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.ext.json.*;



public class doCreateGroupResource<K>  extends ServerResource {
	private static final Logger log = Logger.getLogger(doCreateGroupResource.class.getName());
	
	
		/*Create a new group*/
		@Post
		public Representation doCreateGroup(Representation entity){
			
			//create json response
			JsonRepresentation representation = null;
			try {
				log.info("start POST doCreateGroup() doCreateGroupResource");
				
				Form form = new Form(entity);
				for (Parameter parameter : form) {
			        	log.info("parameter " + parameter.getName());
			   		  	log.info("/" + parameter.getValue());
			        }	
				String p_external_group_id = form.getFirstValue("p_external_group_id");
				String p_external_admin_id = form.getFirstValue("p_external_admin_id");
				String p_name = form.getFirstValue("p_name");
				String p_fb_token = form.getFirstValue("p_fb_token");
			      
				AChiToccaManager.doCreateGroup(p_external_group_id, p_external_admin_id, p_name, p_fb_token, null);
				
				MessageDto msgDto = new MessageDto();
				msgDto.code=0;
				msgDto.result="doCreateGroup completed!";
				
				
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
			
			}catch (NumberFormatException e_ne) {
				e_ne.printStackTrace();
				setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				ErrorResource error = new ErrorResource();
				error.setErrorCode(ErrorResource.NUMBER_FORMAT_ERROR);
				error.setErrorMessage(e_ne.getMessage());
				JsonRepresentation errorRepresentation = new JsonRepresentation(error);
				return errorRepresentation;
				
			}catch (WrongParameterException e_wp) {
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
				log.info("end POST doCreateGroup() doCreateGroupResource");
				
			}
			
			
		}
	
}
