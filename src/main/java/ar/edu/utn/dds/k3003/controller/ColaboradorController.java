package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.app.Fachada;
import ar.edu.utn.dds.k3003.facades.dtos.ColaboradorDTO;
import ar.edu.utn.dds.k3003.facades.dtos.FormaDeColaborarEnum;
import ar.edu.utn.dds.k3003.model.FormasDTO;
import ar.edu.utn.dds.k3003.model.FormulaDTO;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.List;
import java.util.Objects;

public class ColaboradorController {
  Fachada fachada;
  public ColaboradorController(Fachada fachada) {
    this.fachada = fachada;
  }

  public void getColaborador(Context context) {
    try{
      context.json(fachada.buscarXId(Long.parseLong(context.pathParam("id"))));
      context.status(HttpStatus.OK);
    }
    catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al obtener el colaborador: " + e.getMessage());
    }
  }

  public void crearColaborador(Context context) {
    try {
      //En el context llega el DTO del colaborador a agregar, y la fachada me retorna el DTO del colaborador ya agregado
      ColaboradorDTO colaboradorDTOAgregado = fachada.agregar(context.bodyAsClass(ColaboradorDTO.class));
      context.json(colaboradorDTOAgregado);
      context.status(HttpStatus.CREATED);
    }
    catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al agregar el colaborador: " + e.getMessage());
    }
  }

  public void modificarColaborador(Context context) {
    try{
      Long colaboradorId = fachada.buscarXId(Long.parseLong(context.pathParam("id"))).getId();
      List<FormaDeColaborarEnum> formasDeColaborar = context.bodyAsClass(FormasDTO.class).getFormas();
      context.json(fachada.modificar(colaboradorId, formasDeColaborar));
    }
    catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al modificar el colaborador: " + e.getMessage());
    }
  }

  public void getPuntuacionColaborador(Context context) {
    try {
      Long colaboradorId = fachada.buscarXId(Long.parseLong(context.pathParam("id"))).getId();
      context.json(fachada.puntos(colaboradorId));
    }
    catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al obtener la puntuación del colaborador: " + e.getMessage());
    }
  }


  public void modificarPuntuacionMultiplicador(Context context) {
    try {
      FormulaDTO formula = context.bodyAsClass(FormulaDTO.class);
      fachada.actualizarPesosPuntos(
          formula.getPesosDonados(),
          formula.getViandasDistribuidas(),
          formula.getViandasDonadas(),
          formula.getTarjetasRepartidas(),
          formula.getHeladerasActivas()
      );
      context.status(HttpStatus.OK);
      context.result("Fórmula de puntuación modificada correctamente");

    } catch (Exception e) {
      context.status(HttpStatus.BAD_REQUEST);
      context.result("Error al modificar la fórmula de puntuación: " + e.getMessage());
    }
  }

}
