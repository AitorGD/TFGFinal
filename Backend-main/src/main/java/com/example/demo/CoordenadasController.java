package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFDataMgr;
import org.json.simple.JSONArray;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class CoordenadasController {
  
  @GetMapping("/coordenadas")
  public ResponseEntity<String> getCoordenadas() {
	  try {
    String coordenadas = obtenerCoordenadas(); // Método que genera el archivo con las coordenadas
    return ResponseEntity.ok().body(coordenadas);
	  } catch (Exception e) {
		    // Maneja la excepción y devuelve una respuesta de error apropiada
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
		  }
		
  }
  
  @SuppressWarnings("unused")
private String obtenerCoordenadas() {
		
	  //System.out.println(file);
//		Declaracion de variables
		//String del archivo que contiene en rdf
	    String inputFile="incidencias.rdf"; 
		
		//Integers que utilizo en el while (se explica ahí)
		int i = 0;
		//Variables para saber la cantidad de coordenadas
		int longi = 0;
	    int lat = 0;
	    int timm = 0;
	    int timm2 = 0;
	    int timm3 = 0;
	    int timm4 = 0;
	    int numE = 0;
	    int numV = 0;
	    int numG = 0;
	    int numW = 0;
	    int numM = 0;
	    
	    String rdfLink = "https://infocar.dgt.es/datex2/lod/dgt/incidencias.rdf";

	    //Variable de Jena que permite almancenar el rdf
		Model model = ModelFactory.createDefaultModel();
		
		//Lista de la clase incidencias donde se almacenaran las coordenadas. 
		ArrayList<incidencias> listaIncidencias = new ArrayList<incidencias>();
		//Variable que utlizo para comprobaciones y como lista auxiliar
	 	//ArrayList<incidencias> listaIncidenciasSinFiltro = new ArrayList<incidencias>();
		
		if(true) {
			//Leo el archivo y lo guardo en model
			try{
				 InputStream in =new  FileInputStream(inputFile);
				 if(in == null) {
					 System.out.println("Archivo no encontrado");
				 }
				 model.read(in,"RDF/XML");
				  
				  //Mostraría el rdf en consola
				  //model.write(System.out);
			 }catch(Exception e){System.out.println("Error leyendo ;)");}
		}
		else {
			RDFDataMgr.read(model, rdfLink);
		}
		
		 model.write(System.out);

//		  // Intentar algo así model=(Model)file;
//			try {
//				model.read(file.getInputStream(), null);
//				
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}

			
			
			//Un iterador que se utiliza para recorrer el model
			StmtIterator iterador = model.listStatements();
		 	
	        incidencias inc = new incidencias();

//			Recorro el RDFa
		    while (iterador.hasNext()) {
		        Statement stmt = iterador.nextStatement();
		        Resource sujeto = stmt.getSubject();
		        Property predicado = stmt.getPredicate();
		        RDFNode objeto = stmt.getObject();
		        //System.out.println(predicado);


//				Compruebo que existen las propiedades lat y lon y las añado a la Lista	        
		        
		        if (predicado.toString().equals("http://www.w3.org/2003/01/geo/wgs84_pos#lat")) {
		            double latitud = objeto.asLiteral().getDouble();
			        inc.setLatitud(latitud);
//			        System.out.println("Latitud : "+ latitud);
			        lat++;


		        }
		        if (predicado.toString().equals("http://www.w3.org/2003/01/geo/wgs84_pos#lon")) {

		        	double longitud = objeto.asLiteral().getDouble();
			        inc.setLongitud(longitud);
//			        System.out.println("Longitud : "+ longitud);
			        longi++;
		        }
		        if (predicado.toString().equals("http://cef.uv.es/lodroadtran18/def/transporte/dtx_srti#situationRecordFirstSupplierVersionTime")) {

		        	String time = objeto.asLiteral().getString();
			        inc.setTime(time);
//			        System.out.println("Sacando el tiempo here");
			        timm++;
//			    
		        }
		        if (predicado.toString().equals("http://cef.uv.es/lodroadtran18/def/transporte/dtx_srti#situationRecordCreationTime")) {

		        	String time = objeto.asLiteral().getString();
			        inc.setTime(time);
//			        System.out.println("Sacando el tiempo here");
			        timm2++;
//			    
		        }
		        if (predicado.toString().equals("http://cef.uv.es/lodroadtran18/def/transporte/dtx_srti#situationRecordVersionTime")) {

		        	String time = objeto.asLiteral().getString();
			        inc.setTime(time);
//			        System.out.println("Sacando el tiempo here");
			        timm3++;
//			    
		        }
		        if (predicado.toString().equals("http://www.w3.org/2006/time#inXSDDateTime")) {

		        	String time = objeto.asLiteral().getString();
			        inc.setTime(time);
//			        System.out.println("Sacando el tiempo here");
			        timm4++;
//			    
		        }
		        if (predicado.toString().contains("http://cef.uv.es/lodroadtran18/def/transporte/dtx_srti#hasWeatherRelatedRoadConditionTypeEnum")){
		        	inc.setType("WeatherRelatedRoadConditions");
		        	inc.setOcurrencia(stmt.getObject().asResource().getLocalName());
		        	numW++;
		        }
		        if (predicado.toString().contains("EnvironmentalObstruction")){
		        	inc.setType("EnvironmentalObstruction");
		        	inc.setOcurrencia(stmt.getObject().asResource().getLocalName());
		        	numE++;
		        }
		        else if (predicado.toString().contains("http://cef.uv.es/lodroadtran18/def/transporte/dtx_srti#hasRoadMaintenanceType")){
		        	inc.setType("MaintenanceWorks");
		        	inc.setOcurrencia(stmt.getObject().asResource().getLocalName());
		        	numM++;
		        }
		        else if (predicado.toString().contains("http://cef.uv.es/lodroadtran18/def/transporte/dtx_srti#GeneralObstruction")){
		        	inc.setType("GeneralObstruction");
		        	inc.setOcurrencia("Obstrucion");
		        	numG++;
		        }
		        else if (predicado.toString().contains("http://cef.uv.es/lodroadtran18/def/transporte/dtx_srti#hasVehicleObstructionTypeEnum")){
		        	inc.setType("VehicleObstruction");
		        	inc.setOcurrencia(stmt.getObject().asResource().getLocalName());
		        	numV++;
		        }

		        
//				Lista de incidencias para comprobaciones, IGNORAR	        
//				listaIncidenciasSinFiltro.add(inc);
//		        if(inc.getLongitud() != 0 && i%2 == 0) {
		       if(inc.getLongitud() != 0 && inc.getLatitud() != 0 && inc.getTime() != "" ) {
	    		listaIncidencias.add(inc);
//	    		System.out.println("Tiempo = "+ inc.getTime());
//	    		System.out.println("Longitud = "+ inc.getLongitud());
//	    		System.out.println("Latitud = "+ inc.getLatitud());
//	    		System.out.println("Type = "+ inc.getType());

	   	        inc = new incidencias();
		       }
		       i++;
		    }
//
//	        String queryString = "SELECT DISTINCT ?situationRecord \r\n"
//	        		+ "\r\n"
//	        		+ "WHERE \r\n"
//	        		+ "	{\r\n"
//	        		+ "		?situationRecord a http://cef.uv.es/lodroadtran18/def/transporte/dtx_srti:WeatherRelatedRoadConditions.\r\n"
//	        		+ "		\r\n"
//	        		+ "	}";
//	        
//	        Query query = QueryFactory.create(queryString);
//	        
//	        // Ejecutar la consulta sobre el modelo
//	        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
//	            // Obtener los resultados de la consulta
//	            ResultSet results = qexec.execSelect();
//                System.out.println("Hola mundo sigo aqui");
//
//	            // Procesar los resultados
//	            while (results.hasNext()) {
//	                System.out.println("Hola ?");
//
//	                QuerySolution sol = results.next();
//	                RDFNode nombre = sol.get("situacionRecord");
//	                System.out.println("Nombre: " + nombre.toString());
//	            }
//	        }

		    
		    
		    
		    
		    
		    
		    
		    
		    
		    //Aquí comprobaba la cantidad de longitudes y latitudes. Debe haber misma cantidad.
			//System.out.println("Longitud "+ longi +"- Latitud "+lat);
			
//			Una lista de incidencias para comprobar errores. IGNORAR		
		    for(incidencias incidencia : listaIncidencias) {
	    	//System.out.println("ListaSin : "+incidencia.getLongitud()+"-"+incidencia.getLatitud());
		    }
		    System.out.println("Mirando cosas "+ longi+ "-" + timm + "-"+ timm2+ "-"+ timm3 + "-"+ timm4 + "-"+ lat + "-");
		    System.out.println("Mirando cosas 2 "+ numM+ "-" + numW + "-"+ numG + "-" + numE + "-" + numV);
			//Creo un JsonArray para guardarlo todo y recorro la lista.
			JSONArray arr = new JSONArray();
		    for(incidencias incidencia : listaIncidencias) {
		    	JsonObject json = new JsonObject();
		    	//Cada par longitud/latitud es un JSONObject
	    	    json.put("Longitud", incidencia.getStringLongitud());
	    	    json.put("Latitud", incidencia.getStringLatitud());
	    	    json.put("Time", incidencia.getTime());
	    	    json.put("Type", incidencia.getType());
	    	    json.put("Ocurrencia", incidencia.getOcurrencia());

	    	    arr.add(json);
		    }
		    
		    //Muestro el JsonArray
		    //System.out.println(arr);

		    try (PrintWriter out = new PrintWriter(new FileWriter("coorIncidencias.json"))) {
	            out.write(arr.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		    
		    return arr.toString();
	  }
  }
  

