package com.example.facultades.service;

import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Carrera;
import com.example.facultades.repository.ICarreraRepository;
import com.example.facultades.util.IAsociarEntidades;
import com.example.facultades.util.IEntidadAsociable;
import com.example.facultades.util.IRepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarreraService extends GenericService<Carrera, Long> implements ICarreraService, IEntidadAsociable<Carrera> {

    @Autowired
    private ICarreraRepository carreraRepository;

    @Autowired
    private IAsociarEntidades asociarEntidades;

    @Autowired
    private IRepositoryFactory repositoryFactory;

    @Override
    public Carrera save(Carrera carrera) {
        this.asociar(carrera);
        return carreraRepository.save(carrera);
    }

    @Override
    public Carrera update(Carrera carrera) {
       return this.save(carrera);
    }


    @Override
    public void asociar(Carrera carrera) {
        carrera.setListaComentarios(asociarEntidades.relacionar(carrera.getListaComentarios(), repositoryFactory.generarRepositorio(NombreRepositorio.COMENTARIO.getRepoName())));
        carrera.setListaCalificacion(asociarEntidades.relacionar(carrera.getListaCalificacion(), repositoryFactory.generarRepositorio(NombreRepositorio.CALIFICACION.getRepoName())));
    }


   /* @Override
    public List<Carrera> getAll() {
        List<Carrera> listaCarrera = carreraRepository.findAll();
        return  listaCarrera;
    }


    @Override
    public String delete(Long id) {
        carreraRepository.deleteById(id);
        return "Carrera eliminada con exito";
    }

    @Override
    public Optional<Carrera> findById(Long id) {
        Optional<Carrera> carreraOptional = carreraRepository.findById(id);
        return carreraOptional;
    }



    @Override
    public Page<Carrera> obtenerCarrerasPaginadas(Pageable pageable) {
        return carreraRepository.findAll(pageable);
    }

    @Override
    public List<Carrera> getTopCarreras(int cantidadRegistros, int pagina) {
        Pageable pageable = PageRequest.of(cantidadRegistros, pagina);
        List<Carrera> carreras = carreraRepository.getTopCarreras(pageable);
        return carreras;
    }

    @Override
    public void procesarLista(Carrera carrera) {
        carrera.setListaCalificacion(ProcesarLista.procesarLista(carrera.getListaCalificacion(), calificacionService));
        carrera.setListaComentarios(ProcesarLista.procesarLista(carrera.getListaComentarios(), comentarioService));
    }


    //@Override
    //public String eliminarAsociacionUiversidadCarrera(Long id) {
      //  carreraRepository.eliminarAsociacionUniversidadCarrera(id);
       // return "La asociación entre la carrera con el id " + id + " se ha eliminado con exito" ;
    //}

    */
}
