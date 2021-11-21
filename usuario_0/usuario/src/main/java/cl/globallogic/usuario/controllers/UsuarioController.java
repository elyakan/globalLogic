package cl.globallogic.usuario.controllers;

import cl.globallogic.usuario.model.Telefono;
import cl.globallogic.usuario.model.Usuario;
import cl.globallogic.usuario.repositories.TelefonoRepository;
import cl.globallogic.usuario.response.ResponseHandler;
import cl.globallogic.usuario.services.TelefonoService;
import cl.globallogic.usuario.services.UsuarioService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping(value ="/api/v2/usuario",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
public class UsuarioController {
    //private static final Logger logger = (Logger) LoggerFactory.getLogger(UsuarioController.class);
    UsuarioService usuarioService;
    TelefonoService telefonoService;
    @Autowired
    TelefonoRepository telefonoRepository;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }



    //The function receives a GET request, processes it and gives back a list of Todo as a response.
    @GetMapping
    public ResponseEntity<Object> getAllUsuarios() {
        try{
            List<Usuario> usuarios = usuarioService.getUsuarios();
            //return new ResponseEntity<>(usuarios, HttpStatus.OK);
            return ResponseHandler.generateResponse("Successfully get all data!", HttpStatus.OK, usuarios);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("ERROR USUARIO GET ALL: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    
    //The function receives a GET request with id in the url path, processes it and returns a Usuario with the specified Id
    @GetMapping({"/{id}"})
    public ResponseEntity<Object> getUsuario(@PathVariable String id) {
        try{
            //return new ResponseEntity<>(usuarioService.getUsuarioById(id), HttpStatus.OK);
            return ResponseHandler.generateResponse("Successfully get data!", HttpStatus.OK, usuarioService.getUsuarioById(id));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("ERROR USUARIO GET DETAIL: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
    //The function receives a POST request, processes it, creates a new Usuario and saves it to the database and returns a resource link to the created Usuario.
    @PostMapping
    @ResponseBody
    public HttpEntity<? extends Object> saveUsuario(@RequestBody Usuario usuario) {
         try{
            String id = null;
            String id_tel = null;
            List<Telefono> lst_telefono = new ArrayList<Telefono>();

            List<Usuario> lst_usuario = new ArrayList<Usuario>();

            //logger.debug("eeeekkk  kkkkf ffkff  fff");

            JSONObject jsonObject = new JSONObject(usuario);
             String in_email = jsonObject.getString("email");
             String in_pwd = jsonObject.getString("password");

            //System.out.println("validateExp(in_email) : "+ validateExp(in_email,1) );
            if(validateExp(in_email,1)==false){
                return ResponseHandler.generateResponse("The mail is badly formed!", HttpStatus.BAD_REQUEST, null);
            }
             //System.out.println("in_pwd : "+ in_pwd );
             //System.out.println("validateExp(in_pwd) : "+ validateExp(in_pwd,2) );
             if(validateExp(in_pwd,2)==false){
                 return ResponseHandler.generateResponse("The password is badly formed!", HttpStatus.BAD_REQUEST, null);
             }
            usuarioService.getMail(in_email).forEach(lst_usuario::add);
            if (lst_usuario.isEmpty()==false) {
                return ResponseHandler.generateResponse("The mail already registered!", HttpStatus.SEE_OTHER, null);
            }

            usuario.setFcreate(LocalDate.now());
            usuario.setFmodified(LocalDate.now());
            usuario.setLast_login(LocalDate.now());

            Usuario usuario1 = usuarioService.insert(usuario);

            JSONArray jsonArray = jsonObject.getJSONArray("telefono");
            for(int i=0;i<jsonArray.length();i++){
                try {
                    Telefono telefono = new Telefono();
                    JSONObject json = jsonArray.getJSONObject(i);
                    //Aquí se obtiene datos telefono
                    telefono.setId_usuario(usuario1.getId_usuario());
                    id=usuario1.getId_usuario();
                    telefono.setNumber(json.getString("number").toString());
                    telefono.setCitycode(json.getString("citycode").toString());
                    telefono.setContrycode(json.getString("contrycode").toString());

                    Telefono telefono1 = telefonoRepository.save(telefono);
                    lst_telefono.add(telefono1);

                } catch (JSONException e) {
                    return ResponseHandler.generateResponse("ERROR TELEFONO: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
                }
            }
            Usuario usuario2 = usuarioService.getUsuarioById(id);
            usuario2.setTelefono(lst_telefono);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("usuario", "/api/v1/usuario/" + usuario1.getId_usuario().toString());

            //return new ResponseEntity<>(usuario2, httpHeaders, HttpStatus.CREATED);
            return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, usuario2);

        } catch (Exception e) {
            return ResponseHandler.generateResponse("ERROR USUARIO ADD " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    //The function receives a PUT request, updates the Usuario with the specified Id and returns the updated Usuario
    @PutMapping({"/{id}"})
    public ResponseEntity<Object> updateUsuario(@PathVariable("id") String id, @RequestBody Usuario usuario) {
        try {
            List<Usuario> lst_usuario = new ArrayList<Usuario>();

            //logger.debug("eeeekkk  kkkkf ffkff  fff");

            JSONObject jsonObject = new JSONObject(usuario);
            String in_email = jsonObject.getString("email");
            String in_pwd = jsonObject.getString("password");

            //System.out.println("validateExp(in_email) : "+ validateExp(in_email,1) );
            if(validateExp(in_email,1)==false){
                return ResponseHandler.generateResponse("The mail is badly formed!", HttpStatus.BAD_REQUEST, null);
            }
            //System.out.println("in_pwd : "+ in_pwd );
            //System.out.println("validateExp(in_pwd) : "+ validateExp(in_pwd,2) );
            if(validateExp(in_pwd,2)==false){
                return ResponseHandler.generateResponse("The password is badly formed!", HttpStatus.BAD_REQUEST, null);
            }
            usuarioService.getMail(in_email).forEach(lst_usuario::add);
            if (lst_usuario.isEmpty()==false) {
                return ResponseHandler.generateResponse("The mail already registered!", HttpStatus.SEE_OTHER, null);
            }

            usuarioService.updateUsuario(id, usuario);
            return ResponseHandler.generateResponse("Successfully update data!", HttpStatus.OK, usuarioService.getUsuarioById(id));
            //return new ResponseEntity<>(usuarioService.getUsuarioById(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("ERROR USUARIO UPDATE: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }


    //The function receives a DELETE request, deletes the Usuario with the specified Id.
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Object> deleteUsuario(@PathVariable("id") String id) {
      try{
            usuarioService.deleteUsuario(id);
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return ResponseHandler.generateResponse("Successfully delete data!", HttpStatus.OK, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("ERROR USUARIO DELETE: " + e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    public boolean validateExp(String strValidate, Integer tipo) {

        String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


        String regexPwd = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$";

        if(tipo==1) {
            Pattern pattern = Pattern.compile(regexEmail);
            Matcher matcher1 = pattern.matcher(strValidate);
            return matcher1.matches()  ;
        }
        else{
            Pattern pattern = Pattern.compile(regexPwd);
            Matcher matcher = pattern.matcher(strValidate);
            return matcher.matches();
        }

    }

}
