package ufv.dis.final2022.EP;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

@SpringBootApplication
@RestController
public class EpApplication {

	public static void main(String[] args) {

		SpringApplication.run(EpApplication.class, args);
	}

	@GetMapping("/peticiones")
	public String peticiones() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("resultados.json")));
			Gson gson = new Gson();
			String linea = "";
			String finalCode = "";
			while((linea = br.readLine()) != null ){
				finalCode += linea;
			}
			return finalCode;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@PostMapping("/ip")
	public String InformacionPorIp(@RequestBody String IP){
		try {
			Gson gson = new Gson();
			JsonObject miIp = gson.fromJson(IP,JsonObject.class);
			String IpToLong = (miIp.get("ip").toString().replace(".","").replace("\"",""));
			Long IpFinal = Dot2LongIP(IpToLong);

			BufferedReader br = new BufferedReader(new FileReader(new File("LocalizaIP.json")));
			ObjectIP[] listadoIp = gson.fromJson(br,ObjectIP[].class);

			for(ObjectIP a : listadoIp){
				if (IpFinal >= a.getIp_from() && IpFinal <= a.getIp_to()){

					JsonObject objectReturn = new JsonObject();
					objectReturn.addProperty("IP",miIp.get("ip").toString());
					objectReturn.addProperty("Country code",a.getCountry_code());
					objectReturn.addProperty("Region name",a.getRegion_name());
					objectReturn.addProperty("City name",a.getCity_name());
					objectReturn.addProperty("Zipcode",a.getZip_code());
					objectReturn.addProperty("Timezone",a.getTime_zone());
					addUnoContador(miIp.get("ip").toString().replace("\"",""),a);
					return objectReturn.toString();
				}
			}
			br.close();
			JsonObject ErrorReturn = new JsonObject();
			ErrorReturn.addProperty("IP",miIp.get("ip").toString().replace("\"",""));
			ErrorReturn.addProperty("Mensaje","IP no encontrada");
			addUnoContadorErroneo(miIp.get("ip").toString().replace("\"",""));
			return ErrorReturn.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
	//public void addUnoContador(ObjectIP ip,boolean CiertoTrueErroneoFalse){
	public static void addUnoContador(String ipString, ObjectIP ip){
		try {
			Gson gson = new Gson();
			JsonArray arrayElementos = new JsonArray();
			JsonArray ElementosCorrectos = new JsonArray();
			JsonArray ElementosFallidos = new JsonArray();
			int contadorExacto = 0;

			File f = new File("resultados.json");
			if(!f.exists()){
				f.createNewFile();
			}
				BufferedReader br = new BufferedReader(new FileReader(f));
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(br);
				arrayElementos = element.getAsJsonArray();
				System.out.println(element.getAsJsonArray().get(0) + "MI ARRAY");
				ElementosCorrectos = arrayElementos.get(0).getAsJsonArray();
				ElementosFallidos = arrayElementos.get(1).getAsJsonArray();

				for (int i = 0; i < ElementosCorrectos.size() ; i++) {
					System.out.println(ElementosCorrectos.get(i));
					JsonObject rA = ElementosCorrectos.get(i).getAsJsonObject();
					if (rA.get("ip").toString().replace("\"","").equals(ipString)){
						contadorExacto = Integer.parseInt(String.valueOf(rA.get("count")));
						System.out.println(contadorExacto + "EL CONTADOR IGUALADO" + rA.get("count") + "LO QUE DEBERIA TENER");

					}
					ElementosCorrectos.remove(rA);
				}



				contadorExacto = contadorExacto + 1;
				JsonObject objetoCorrecto = new JsonObject();
				objetoCorrecto.addProperty("ip",ipString);
				objetoCorrecto.addProperty("count",contadorExacto);
				objetoCorrecto.addProperty("country_code",ip.getCountry_code());
				objetoCorrecto.addProperty("country_name",ip.getCountry_name());
				objetoCorrecto.addProperty("region_name",ip.getRegion_name());
				objetoCorrecto.addProperty("city_name",ip.getCity_name());
				objetoCorrecto.addProperty("time_zone",ip.getTime_zone());
				objetoCorrecto.addProperty("latitude",ip.getLatitude());
				objetoCorrecto.addProperty("longitude",ip.getLongitude());
				objetoCorrecto.addProperty("zip_code",ip.getZip_code());

				ElementosCorrectos.add(objetoCorrecto);


			JsonArray arraySave = new JsonArray();
			arraySave.add(ElementosCorrectos);
			arraySave.add(ElementosFallidos);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(arraySave.toString());
			bw.flush();
			bw.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static void addUnoContadorErroneo(String ipString){
		try {
			Gson gson = new Gson();
			JsonArray arrayElementos = new JsonArray();
			JsonArray ElementosCorrectos = new JsonArray();
			JsonArray ElementosFallidos = new JsonArray();
			int contadorExacto = 0;

			File f = new File("resultados.json");
			if(!f.exists() ){
				f.createNewFile();
			}else{
				BufferedReader br = new BufferedReader(new FileReader(f));
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(br);
				arrayElementos = element.getAsJsonArray();
				System.out.println(element.getAsJsonArray().get(0) + "MI ARRAY");
				ElementosCorrectos = arrayElementos.get(0).getAsJsonArray();
				ElementosFallidos = arrayElementos.get(1).getAsJsonArray();
			}




				for (int i = 0; i < ElementosFallidos.size() ; i++) {
					JsonObject rB = ElementosFallidos.get(i).getAsJsonObject();
					if(rB.get("ip").equals(ipString) && rB.get("ip") != null){
						contadorExacto = Integer.parseInt(String.valueOf(rB.get("count")));
						ElementosFallidos.remove(rB);
					}

				}

				contadorExacto = contadorExacto + 1;
				JsonObject objetoErroneo = new JsonObject();
				objetoErroneo.addProperty("ip",ipString);
				objetoErroneo.addProperty("count",contadorExacto + 1);
				ElementosFallidos.add(objetoErroneo);

				JsonArray arraySave = new JsonArray();
				arraySave.add(ElementosCorrectos);
				arraySave.add(ElementosFallidos);

			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(arraySave.toString());
			bw.flush();
			bw.close();


		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String longToIp(long ip) {
		StringBuilder result = new StringBuilder(15);
		for (int i = 0; i < 4; i++) {
			result.insert(0, Long.toString(ip & 0xff));
			if (i < 3) {
				result.insert(0, '.');
			}
			ip = ip >> 8;
		}
		return result.toString();
		//192.168.1.2 -> 3232235778
	}

	public static Long Dot2LongIP(String dottedIP) {
		String[] addrArray = dottedIP.split("\\.");
		long num = 0;
		for (int i=0;i<addrArray.length;i++) {
			int power = 3-i;
			num += ((Integer.parseInt(addrArray[i]) % 256) * Math.pow(256,power));
		}
		return num;
	}

}
