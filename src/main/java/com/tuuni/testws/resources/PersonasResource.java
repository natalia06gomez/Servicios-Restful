package com.tuuni.testws.resources;

import com.tuuni.testws.model.Persona;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("/personas")
public class PersonasResource {

    private static final List<Persona> personas = new CopyOnWriteArrayList<>();

    static {
        personas.add(new Persona(1, "Persona 1", 20));
        personas.add(new Persona(2, "Persona 2", 30));
        personas.add(new Persona(3, "Persona 3", 40));
    }

    // (Extra útil para ver todo)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Persona> listarJson() {
        return personas;
    }

    // ✅ Requerimiento: "Adicionar una nueva Persona a la lista."
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregar(Persona nueva) {
        if (nueva == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Body vacío"))
                    .build();
        }
        if (nueva.getId() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El id debe ser > 0"))
                    .build();
        }
        boolean existe = personas.stream().anyMatch(p -> p.getId() == nueva.getId());
        if (existe) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("error", "Ya existe una persona con id " + nueva.getId()))
                    .build();
        }

        // fuerza cálculo salario (si llegó edad)
        nueva.setEdad(nueva.getEdad());

        personas.add(nueva);
        return Response.status(Response.Status.CREATED).entity(nueva).build();
    }

    // Requerimiento: "Nuevo servicio REST para conocer salario promedio en XML"
    // Para evitar problemas de JAXB, devolvemos XML como String.
    @GET
    @Path("/promedio-salarios")
    @Produces(MediaType.APPLICATION_XML)
    public String promedioSalariosXml() {
        double promedio = personas.stream()
                .mapToDouble(Persona::getSalario)
                .average()
                .orElse(0.0);

        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<salarioPromedio>"
                + "<promedio>" + promedio + "</promedio>"
                + "</salarioPromedio>";
    }
    
    @GET
    @Path("/suma-salarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Double> sumaSalariosJson() {
        double suma = personas.stream()
                .mapToDouble(Persona::getSalario)
                .sum();

        return Map.of("suma", suma);
    }
}
