package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Mesas;
import com.mechi.restaurant.modelos.TiposPlatos;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/tipoPlato")
public class TipoPlatoController {

    @RequestMapping("/tiposPlatos")
    public Map<String, Object> getItems(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<TiposPlatos> items = DAO.getInstance().getTiposPlatos(currentPage, itemsPerPage);
        json.put("items", items);
        json.put("totalItems", DAO.getInstance().getSize(TiposPlatos.class,""));
        return json;
    }
    /*    public List<TiposPlatos> getItemsTiposPlatos() {
     return DAO.getInstance().getTiposPlatos();
     }*/

    @RequestMapping("/tiposPlato")
    public TiposPlatos greetingTp(@RequestParam(value = "id", defaultValue = "1") int id) {
        return DAO.getInstance().getTiposPlato(id);
    }

    @RequestMapping("/agregar")
    public TiposPlatos agregarTipoPlato(@RequestBody TiposPlatos tipoPlato) {
        DAO.getInstance().add(tipoPlato);
        return tipoPlato;
    }

    @RequestMapping("/modificar/{id}")
    public TiposPlatos modificarTipoPlato(@RequestBody TiposPlatos tipoPlato, @PathVariable("id") int id) {
        DAO.getInstance().update(tipoPlato);
        return tipoPlato;
    }

    @RequestMapping("/borrar/{id}")
    public boolean BorrarTipoPlato(@PathVariable("id") int id) {
        TiposPlatos tp = DAO.getInstance().getTiposPlato(id);
        tp.setActivo(false);
//        if (tp.getPlatos().isEmpty() && tp.getBebidas().isEmpty()) {
//            DAO.getInstance().delete(tp);
//            return true;
//        }
        DAO.getInstance().update(tp);
        return true;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file) {
        String filename = null;
        if (!file.isEmpty()) {
            try {
                filename = "src/main/webapp/images/" + file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream
                        = new BufferedOutputStream(new FileOutputStream(new File(filename)));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + filename + "!";
            } catch (Exception e) {
                return "You failed to upload " + filename + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + filename + " because the file was empty.";
        }
    }

}
