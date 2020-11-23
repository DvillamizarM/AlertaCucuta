package com.example.alertacucuta;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuxMethods {
    public String aux = "help";
    public static String[] listaBarrios = {"Seleccionar", "CALLEJON", "CENTRO", "CONTENTO", "LA PLAYA", "LA SEXTA", "LATINO", "LLANO",
            "PARAMO", "ACUARELA", "BALCONES DE SAN FRANOSCO", "BLANCO", "BRISAS DEL PAMPLONITA", "CAOBOS", "CEIBA I",
            "CEIBA II", "COLSAG", "COMERCIAL BOLIVAR", "CONDADO DE CASTILLA", "EL ROSAL", "GALICIA", "GOVIKA",
            "HACARITAMA", "HURAPANES", "LA CAPILLANA", "LA CASTELLANA", "LA CEIBA", "LA ESPERANZA", "LA PRIMAVERA",
            "LA RINCONADA", "LA RIVIERA", "LAS ALMEYDAS", "LIBERTADORES", "LOS ACACIOS", "LOS PINOS", "LOS PROCERES",
            "MANOLO LEMUS", "MIRADOR CAMPESTRE", "PALMA REAL", "PARQUE REAL", "PARQUES RESIDENCIALES LOS LIBERTADORES",
            "POPULAR", "PRADOS CLUB", "PRADOS I", "PRADOS II", "QUINTA BOSCH", "QUINTA ORIENTAL", "QUINTA VELE",
            "RINCON DE LOS PRADOS SAN ISIDRO", "SAN REMO", "SANTA LUCIA", "SAYAGO", "THE RIVERS COUNTRY",
            "VALPARAISO SUIT", "VILLA MARIA", "AGUAS CALIENTES", "BELLAVISTA", "BETHEL", "BOCONO", "BOGOTA",
            "LA CAROLINA", "LA LIBERTAD", "LA UNION", "LAS MARGARITAS", "LUIS PEREZ HERNANDEZ", "MORELLY",
            "POLICARPA", "SAN MATEO", "SANTA ANA", "URBANIZACION SANTA ANA VALLE ESTHER", "13 DE MARZO",
            "ALTO PAMPLONITA", "ANIVERSARIO I", "ANIVERSARIO II", "BAJO PAMPLONITA", "BOSQUES DEL PAMPLONITA",
            "CAÑAFISTOLO", "EL HIGUERON", "ESTACION DEL ESTE", "HELIOPOLIS", "LA ALAMEDA", "LA CAMPIÑA LA FLORIDA",
            "LA ISLA", "LA QUINTA", "NUEVA SANTA CLARA", "NUEVO ESCOBAL", "PORTAL DEL ESCOBAL", "PRADOS DEL ESTE",
            "CONJUNTO PRADOS DEL ESTE SAN JOSE", "SAN LUIS", "SAN MARTIN I", "SAN MARTIN II", "SANTA FE RESITA",
            "SANTILLANA", "TERRANOVA", "TORCOROMA", "TORCOROMA SIGLO XXI", "VIEJO ESCOBAL", "VILLA CAMILA",
            "VILLAS DE SAN DIEGO", "ALCALA", "CIUDAD JARDIN", "COLPET", "EL BOSQUE", "GRATAMIRA", "GUAIMAFtAL",
            "GUALANDAY", "JUANA RANGEL DE CUEllAR LA MAR", "LA MARIA", "LA MERCED", "UNARES", "LLERAS RESTREPO",
            "LOS ANGELES", "NIZA", "PARAISO", "PESCADERO", "PORTACHUELO", "PRADOS DEL NORTE", "SAN EDUARDO I Y II",
            "SANTA HELENA", "SEVILLA", "TASAJERO", "ZULIMA I,II,III Y IV ETAPA", "20 DE DICIEMBRE", "6 DE MAYO",
            "8 DE DICIEMBRE", "AEROPUERTO", "ALONSITO", "BRISAS DEL AEROPUERTO", "BRISAS DEL NORTE",
            "BRISAS DEL PARAISO", "CAMILO DAZA", "CAÑO LIMON", "CARLOS GARCIA LOZADA", "CARLOS PIZARRO", "CECIUA CASTRO",
            "CERRO DE LA CRUZ", "CERRO NORTE", "COLINAS DE LA VICTORIA", "COLINAS DEL SALADO",
            "CONJ. CERRADO MOLINOS DEL NORTE", "CUMBRES DEL NORTE", "DIVINO NIÑO", "EL CERRO", "El SALADO",
            "ESPERANZA MARTIN", "GARCIA HERREROS I Y II", "LA CONCORDIA", "LA INSULA", "LIMONAR DEL NORTE",
            "LOS LAURELES", "MARIA AUXILIADORA", "MARIA PAZ", "MOLINOS DEL NORTE", "NUEVA COLOMBIA", "OLGA TERESA",
            "PANAMERICANO", "PORVENIR", "RAFAEL NUÑEZ", "SAN GERARDO", "SIMON BOLIVAR I", "TOLEDO PLATA",
            "TRIGAL DEL NORTE", "URBANIZCION LAS AMERICAS", "VILLA JULIANA", "VILLA NUEVA", "VILLAS DEL TEJAR",
            "VIRGILIO BARCO", "BUENOS AIRES", "CHAPINERO", "CLARET", "COLOMBIA I Y II", "COMUNEROS", "EL PARAISO",
            "EL ROSAL DEL NORTE", "LA FLORIDA", "LA HERMITA", "LA LAGUNA", "LA PRIMAVERA", "LAS AMÉRICAS", "MOTILONES",
            "OSPINA PERA", "TUCUNARE", "6 DE ENERO", "7 DE AGOSTO", "ANTONIA SANTOS", "ATALAYA",
            "ATALAYA I, II Y III ETAPA", "BELISARIO", "CARLOS RAMIREZ PARIS", "CERRO PICO", "CUCUTA 75", "DOÑA NIDIA",
            "EL DESIERTO", "EL MINUTO DE DIOS", "EL PROGRESO", "EL RODEO", "JUAN RANGEL", "LA CAROLINA", "LA VICTORIA",
            "LOS ALMENDROS", "LOS OLIVOS", "NIÑA CECI", "NUEVO HORIZONTE", "PALMERAS", "VALLES DEL RODEO",
            "28 DE FEBRERO", "AISLANDIA", "ARNULFO BRICEÑO", "BARRIO NUEVO", "BELEN", "BELEN DE UMBRIA",
            "CARORA", "CUNDINAMARCA", "DIVINA PASTORA", "EL REPOSO", "FATIMA", "LAS COLINAS", "LOMA DE BOLIVAR",
            "LOS ALPES", "PUEBLO NUEVO", "RUDESINDO SOTO", "SAN MIGUEL", "ALFONSO LOPEZ", "CAMILO TORRES",
            "CIRCUNVALACION", "CUBEROS NIÑO", "EL RESUMEN", "GAITAN", "GALAN", "LA AURORA", "LA CABRERA",
            "LAS MALVINA",  "MAGDALENA", "PUENTE BARCO", "SAN JOSE", "SAN RAFAEL", "SANTANDER", "SANTO DOMINGO"};

    public static String[] tiposAccidentes = {"Seleccionar","Intoxicaciones", "Quemadura", "Herida", "Electrocucion", "Choque",
            "Atropello", "Volcadura", "Ataque por animal", "Incendio"};
    public static String[] tiposCrimenes = {"Seleccionar", "Asesinato", "Violación", "Asalto y Agresión", "Robo de Vehículo", "Homicidio",
            "Asalto Sexual", "Violencia Doméstica", "Robo", "Trafico de Drogas"};
    public static String[] tiposDesastres = {"Seleccionar", "Corrimiento de tierra", "Ola de calor", "Granizo", "Sequía",
            "Huracán", "Tormenta", "Hambruna", "Incendios", "Inundación", "Terremoto", "Contaminación de cuencas hídricas",
            "Derramamiento del petróleo"};

    public static String[] hora = {"-", "1","2","3","4","5","6","7","8","9","10","11","12"};
    public static String[] minuto = {"-", "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30",
            "31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
    public static String[] time = {"-","AM","PM"};

    public static void createSpinner(String[] list, Spinner spinner, Context context){
        List<String> lista = new ArrayList<String>(list.length);
        lista.addAll(Arrays.asList(list));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
