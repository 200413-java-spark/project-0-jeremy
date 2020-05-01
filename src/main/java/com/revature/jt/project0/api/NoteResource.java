package com.revature.jt.project0.api;

import com.revature.jt.project0.model.Note;
import com.revature.jt.project0.db.NoteDataSource;
import com.revature.jt.project0.db.NoteSQL;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/notes")
public class NoteResource {
    private NoteSQL db = new NoteSQL(NoteDataSource.getInstance());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getAllNotes() {
        return db.getAllNotes();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Note getNote(@PathParam("id") String id) {
        return db.getNote(Integer.parseInt(id));
    }

    @GET
    @Path("latest{n}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getLatest(@PathParam("n") String n) {
        return db.getLatest(Integer.parseInt(n));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertNote(Note note) {
        db.insertNote(note);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNote(@PathParam("id") String id, Note note) {
        note.setId(Integer.parseInt(id));
        db.updateNote(note);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteNote(@PathParam("id") String id) {
        db.deleteNote(Integer.parseInt(id));
    }
}
