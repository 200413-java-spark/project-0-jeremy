package com.revature.jt.project0.api;

import com.revature.jt.project0.db.NoteSQL;
import com.revature.jt.project0.model.Note;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/notes")
public class NoteResource {
    private NoteSQL getDb() {
        Context ctx = null;
        DataSource ds = null;
        try {
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/DS");

        } catch (NamingException e) {
            e.printStackTrace();
        }
        return new NoteSQL(ds);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getAllNotes() {
        return getDb().getAllNotes();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Note getNote(@PathParam("id") String id) {
        return getDb().getNote(Integer.parseInt(id));
    }

    @GET
    @Path("latest")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getLatest() {
        return getDb().getLatest(5);
    }

    @GET
    @Path("latest/{n}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getLatest(@PathParam("n") String n) {
        return getDb().getLatest(Integer.parseInt(n));
    }

    @GET
    @Path("categories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getCategories() {
        return getDb().getCategories();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertNote(Note note) {
        getDb().insertNote(note);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNote(@PathParam("id") String id, Note note) {
        note.setId(Integer.parseInt(id));
        getDb().updateNote(note);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteNote(@PathParam("id") String id) {
        getDb().deleteNote(Integer.parseInt(id));
        return Response.ok().build();
    }
}
