package com.example.facultades.service;


import com.example.facultades.generics.GenericService;
import com.example.facultades.generics.IGenericRepository;
import com.example.facultades.model.Permiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermisoService extends GenericService<Permiso, Long> implements IPermisoService {

    @Autowired
    private IGenericRepository<Permiso, Long> genericRepository;

    @Autowired
    private INotificacionService notificacionService;

    @Override
    public Permiso save(Permiso permiso){
        Permiso permisoGuardado = genericRepository.save(permiso);
        //notificacionService.enviarNotificacionByWebSocket("/tema/admin/notificacion", "Se creo un nuevo permiso");
        notificacionService.guardarNotificacionAdmin(permisoGuardado.getId(), "Se creo un permiso :");
        return permisoGuardado;
    }

}
