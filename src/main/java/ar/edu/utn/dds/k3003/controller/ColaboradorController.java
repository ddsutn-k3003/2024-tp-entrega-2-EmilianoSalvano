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
    Long colaboradorId = Long.parseLong(context.pathParam("id"));
    context.json(fachada.buscarXId(colaboradorId));
  }

  public void crearColaborador(Context context) {
    //En el context llega el DTO del colaborador a agregar, y la fachada me retorna el DTO del colaborador ya agregado
    ColaboradorDTO colaboradorDTOAgregado = fachada.agregar(context.bodyAsClass(ColaboradorDTO.class));
    context.json(colaboradorDTOAgregado);
    context.status(HttpStatus.CREATED);
  }

  public void modificarColaborador(Context context) {
    Long colaboradorId = Long.parseLong(context.pathParam("id"));
    List<FormaDeColaborarEnum> formasDeColaborar = context.bodyAsClass(FormasDTO.class).getFormas();
    context.json(fachada.modificar(colaboradorId, formasDeColaborar));
  }

  public void getPuntuacionColaborador(Context context) {
    Long colaboradorId = Long.parseLong(context.pathParam("id"));
    context.json(fachada.puntos(colaboradorId));
  }


  public void modificarPuntuacionMultiplicador(Context context) {
    FormulaDTO formula = context.bodyAsClass(FormulaDTO.class);
    fachada.actualizarPesosPuntos(
        formula.getPesosDonados(),
        formula.getViandasDistribuidas(),
        formula.getViandasDonadas(),
        formula.getTarjetasRepartidas(),
        formula.getHeladerasActivas()
    );
    context.status(HttpStatus.OK);
  }
}
