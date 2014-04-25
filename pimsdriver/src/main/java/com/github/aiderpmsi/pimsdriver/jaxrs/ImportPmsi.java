package com.github.aiderpmsi.pimsdriver.jaxrs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.github.aiderpmsi.pimsdriver.dao.ImportPmsiDTO;
import com.github.aiderpmsi.pimsdriver.model.PmsiUploadElementModel;

@Path("/import") 
@PermitAll
public class ImportPmsi {
	
	@POST
    @Path("/rsfrss")
	@Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response importPmsi (
    		@FormDataParam("month") Integer month,
    		@FormDataParam("year") Integer year,
    		@FormDataParam("finess") String finess,
    		@FormDataParam("rsf") InputStream rsf,
			@FormDataParam("rsf") FormDataContentDisposition rsfInformations,
    		@FormDataParam("rss") InputStream rss,
			@FormDataParam("rss") FormDataContentDisposition rssInformations,
			@Context UriInfo uriInfo) throws IOException {
		
		// CREATES THE MODEL
		PmsiUploadElementModel model = new PmsiUploadElementModel();
		model.setMonth(month);
		model.setYear(year);
		model.setFiness(finess);
		
		// VALIDATES THE MODEL
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PmsiUploadElementModel>> constraintViolations =
				validator.validate(model);		
		
		if (constraintViolations.size() != 0) {
			// IF ERROR IN THE VALIDATION, SEND AN ERROR
			Response resp =
					Response.status(Status.NOT_ACCEPTABLE).
					header("exception", constraintViolations).
					entity("Erreurs de données dans le post").build();
			throw new WebApplicationException(resp);
		}
		// IF NO ERROR IN THE FORM, CHECK THAT WE HAVE AT LEAST ONE RSF
		else if (rsf == null) {
			Response resp =
					Response.status(Status.NOT_ACCEPTABLE).
					entity("Un RSF au moins doit être sélectionné").build();
			throw new WebApplicationException(resp);
		}
		// THE FORM IS OK AND WE HAVE AT LEAST ONE RSF, IMPORT THE DATAS
		else {
			ImportPmsiDTO ipd = new ImportPmsiDTO();
			ipd.importPmsi(model, rsf, rss);

			// REDIRECT TO OK WINDOW
			Response resp =
					Response.status(Status.OK).build();
			return resp;
		}
    }

}
