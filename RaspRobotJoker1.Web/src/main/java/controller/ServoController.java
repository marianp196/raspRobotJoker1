/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import applicationBuilder.ApplicationBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import component.IWriteableComponent;
import domain.dto.Movement;
import domain.dto.Rotation;
import java.io.IOException;
import java.security.KeyException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.IComponentProvider;

/**
 *
 * @author marian
 */

@Path("/servo/pvmdriver/{id}")
public class ServoController {
    
    @POST
    @Consumes("application/json")
    public Response Write(@PathParam("id") String id, final String body) 
            throws IOException, Exception{        
        
        return execute(id, new Function<IWriteableComponent<Movement, Rotation>, Rotation>() {
           @Override
           public Rotation apply(IWriteableComponent<Movement, Rotation> t) {
                ObjectMapper mapper = new ObjectMapper();       
                               
               try {
                   Movement mv = mapper.readValue(body, Movement.class);
                   return t.Write(mv);//So richtig geil ist das mit delegaten in Java irgendwie nicht.
               } catch (Exception ex) {
                   Logger.getLogger(ServoController.class.getName()).log(Level.SEVERE, null, ex);
               }
               return null;
           }
       });
    }
    
    @GET
    @Produces("application/json")
    public Response Read(@PathParam("id") String id) throws Exception{
       return execute(id, new Function<IWriteableComponent<Movement, Rotation>, Rotation>() {
           @Override
           public Rotation apply(IWriteableComponent<Movement, Rotation> t) {
               try {
                   return t.Read();//So richtig geil ist das mit delegaten in Java irgendwie nicht.
               } catch (Exception ex) {
                   Logger.getLogger(ServoController.class.getName()).log(Level.SEVERE, null, ex);
               }
               return null;
           }
       });
    }
    
    private <TWrite, TRead>Response execute(String id ,Function<IWriteableComponent<TWrite, TRead>,Rotation> exec) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            IComponentProvider provider = ApplicationBuilder.Build();
            
            IWriteableComponent<TWrite, TRead> comp
                    = provider.GetExecuteable(id, "servo");
            
            Rotation rotation = exec.apply(comp);
            
            return Response.ok(mapper.writeValueAsString(rotation),
                    MediaType.APPLICATION_JSON).build();
        } catch (KeyException keyException) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
