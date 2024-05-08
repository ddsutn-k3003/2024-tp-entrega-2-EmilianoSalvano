package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.Colaborador;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ColaboradorRepository {
  private static AtomicLong seqId = new AtomicLong();
  private final Collection<Colaborador> colaboradores;

  public ColaboradorRepository() {
    this.colaboradores = new ArrayList<>();
  }

  public Colaborador save(Colaborador colaborador) {
    if(Objects.isNull(colaborador.getID())) {
      colaborador.setID(seqId.getAndIncrement());
      this.colaboradores.add(colaborador);
    }
    return colaborador;
  }

  public Colaborador findById(Long id) {
    Optional<Colaborador> first = colaboradores.stream().filter(colaborador -> colaborador.getID().equals(id)).findFirst();
    return first.orElseThrow(() -> new NoSuchElementException("No se encontro el colaborador con el id: " + id));
  }
}
