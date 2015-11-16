package org.achitocca.rest.test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.ext.json.JsonRepresentation;

public class GroupTest {
	private String createGroupRequest = "http://localhost:8888/app/achitocca/group/create";
	private String getGroupsRequest = "http://localhost:8888/app/achitocca/group/getgroups";
	private String addUsersRequest = "http://localhost:8888/app/achitocca/group/addusers";
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createGrouptest() throws JSONException {
		Client providerClient = new Client(Protocol.HTTP);
		Request providerRequest = new Request(Method.POST, createGroupRequest);
		
		//create a post entity for Representation
		Form fParam_prov = new Form();
		fParam_prov.add("p_external_group_id","extGroupId");
		fParam_prov.add("p_external_admin_id", "externalAdminId");
		fParam_prov.add("p_name", "test group");
		fParam_prov.add("p_fb_token", "fbtoken");
		
		providerRequest.setEntity(fParam_prov.getWebRepresentation());
		
		Response providerResponse = providerClient.handle(providerRequest);
		System.out.println(providerResponse);		
		JSONObject jsonobj_prov = new JsonRepresentation(providerResponse.getEntityAsText()).getJsonObject();
		
		System.out.println(jsonobj_prov.toString());
		JSONObject json_detail = jsonobj_prov.getJSONObject("detail");
		String groupId = json_detail.getString("groupId");
		
		//add some users
		providerRequest = new Request(Method.POST, addUsersRequest);
		fParam_prov = new Form();
		fParam_prov.add("p_group_id",groupId);
		fParam_prov.add("p_external_user_id", "externalAdminId");
		fParam_prov.add("p_users", "extuser1, extuser2, extuser3");
		fParam_prov.add("p_fb_token", "fbtoken");
		
		providerRequest.setEntity(fParam_prov.getWebRepresentation());
		
		providerResponse = providerClient.handle(providerRequest);
		System.out.println(providerResponse);		
		jsonobj_prov = new JsonRepresentation(providerResponse.getEntityAsText()).getJsonObject();
		
		System.out.println(jsonobj_prov.toString());
		
		//fail("Not yet implemented");
	}
	
	
	@Test
	public void getGroupsTest() throws JSONException {
		Client providerClient = new Client(Protocol.HTTP);
		Request providerRequest = new Request(Method.GET, getGroupsRequest);
		
		//create a post entity for Representation
		Form fParam_prov = new Form();
		fParam_prov.add("p_external_user_id","extUserId");
		fParam_prov.add("p_fb_token", "fbtoken");
		
		providerRequest.setEntity(fParam_prov.getWebRepresentation());
		
		Response providerResponse = providerClient.handle(providerRequest);
		System.out.println(providerResponse);		
		JSONObject jsonobj_prov = new JsonRepresentation(providerResponse.getEntityAsText()).getJsonObject();
		
		System.out.println(jsonobj_prov.toString());
		//fail("Not yet implemented");
	}

}
