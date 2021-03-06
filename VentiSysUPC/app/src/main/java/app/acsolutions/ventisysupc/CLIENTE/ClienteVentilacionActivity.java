package app.acsolutions.ventisysupc.CLIENTE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.acsolutions.ventisysupc.LISTAS.VerVentilacion;
import app.acsolutions.ventisysupc.LISTAS.VerVentilacionAdaptador;
import app.acsolutions.ventisysupc.R;

public class ClienteVentilacionActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView LISTA;
    List<VerVentilacion> ventilaciones;
    VerVentilacionAdaptador adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Button BACK;
    private ProgressDialog PROGRESO;


    String CODIGO,DATO;


    ArrayList<String> CLASES = new ArrayList<>();
    ArrayList<String> FECHAS = new ArrayList<>();

    ArrayList<String> CARRER = new ArrayList<>();
    ArrayList<String> CURSOO = new ArrayList<>();
    ArrayList<String> SECCIO = new ArrayList<>();
    ArrayList<String> FECHAA = new ArrayList<>();
    ArrayList<String> DATOTE = new ArrayList<>();
    ArrayList<String> STRONG = new ArrayList<>();


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_ventilacion);
        BACK = findViewById(R.id.back);
        LISTA = findViewById(R.id.lista);
        LISTA.setLayoutManager(new LinearLayoutManager(this));
        ventilaciones = new ArrayList<>();
        adapter = new VerVentilacionAdaptador(ventilaciones);
        LISTA.setAdapter(adapter);

        Bundle EXTRAS = getIntent().getExtras();
        if (EXTRAS == null){ CODIGO = "NO"; }
        else { CODIGO = EXTRAS.getString("CODIGO"); }

        PROGRESO = ProgressDialog.show(this,"??Cargando Informaci??n!", "Por favor espere...",false,false);
        db.collection("asistencias").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String[] coso = document.getId().split("_");
                        Log.e("ssss: ",document.getId());
                        if (CODIGO.equals(coso[6])){
                            DATO = document.getData().toString();
                            DATOTE.add(DATO);
                            CARRER.add(coso[1]);
                            CURSOO.add(coso[2]);
                            SECCIO.add(coso[3]);
                            FECHAA.add(coso[4]);
                            STRONG.add(document.getId());
                            Log.e("DDDDD: ",coso[1] + " " + coso[2] );
                            BuscarDatos(coso[1],coso[2]);
                            FECHAS.add(coso[4].substring(0,2)+"/"+coso[4].substring(2,4)+"/"+coso[4].substring(4,8));
                        }
                    }
                }
                else { Toast.makeText(ClienteVentilacionActivity.this, "Error de Conexi??n", Toast.LENGTH_LONG).show(); }
                ventilaciones.removeAll(ventilaciones);
                if (CARRER.size()>0){
                    for (int I = 0;I<CARRER.size();I++){
                        VerVentilacion CADENA = new VerVentilacion(FECHAS.get(I),(CLASES.get(I)));
                        ventilaciones.add(CADENA);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    LISTA.setVisibility(View.INVISIBLE);
                }
                PROGRESO.dismiss();
            }
        });
        adapter.setOnItemClickListener(new VerVentilacionAdaptador.OnItemClickListener() {
            @Override
            public void onItemClick(VerVentilacion ventilaciones) {
                Intent INT = new Intent( ClienteVentilacionActivity.this, ClienteDetalleVentilacionActivity.class) ;
                String[] COSO = ventilaciones.getCURSO().split(": ");
                int k = CLASES.indexOf(COSO[1]);
                String HH = STRONG.get(k);
                String[] UU = HH.split("_");
                HH = UU[0]+"_"+UU[1]+"_"+UU[2]+"_"+UU[3]+"_"+UU[4]+"_"+UU[5];
                INT.putExtra("CODIGO",HH);
                startActivity(INT);
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }
    }

    private void BuscarDatos(String CARR,String CURSO) {
        String[] NOM_ELE = {"Introducci??n a la Electr??nica", "Software para Ingenier??a", "Circuitos L??gicos Digitales", "Redes De Comunicaciones 1", "An??lisis de Circuitos El??ctricos 1", "Programaci??n de Computadoras", "Sistemas Digitales", "An??lisis de Circuitos El??ctricos 2", "Dispositivos y Circuitos Anal??gicos", "Microcontroladores", "Programaci??n Avanzada de Computadoras", "Redes de Comunicaciones 2", "Sensores y Actuadores", "Se??ales y Sistemas", "Dise??o de Circuitos Electr??nicos", "Ingenier??a de Comunicaciones", "Procesamiento Digital de Se??ales", "Procesamiento Avanzado de Se??ales e Im??genes", "Sistemas Embebidos", "Telecomunicaciones Digitales", "Proyecto Electr??nico 1", "Rob??tica e Inteligencia Artificial", "Sistemas Operativos en Tiempo Real", "Sistemas de Automatizaci??n Industrial", "Proyecto Electr??nico 2"};
        String[] COD_ELE = {"EL249", "EL227", "EL245", "EL190", "EL243", "EL172", "EL253", "EL244", "EL246", "EL174", "EL184", "EL229", "EL251", "EL231", "EL223", "EL217", "EL222", "EL228", "EL234", "EL186", "EL236", "EL109", "EL226", "EL252", "EL237"};
        String[] NOM_ADM = {"Fundamentos de la Gerencia","Dise??o Organizacional y Procesos","Administraci??n de Operaciones","Grandes Ideas en Gerencia","Business Intelligence & Predictability","Direcci??n Estrat??gica","Design Thinking","Estrategias de Negociaci??n","Planificaci??n Estrat??gica Aplicada","Emprendimiento de Negocios Sostenibles: Formulaci??n","Introducci??n a los Negocios Internacionales","Inteligencia Comercial Internacional","Comercio Internacional","Plan Comercial Internacional","Aduanas","Costos y Cotizaciones Internacionales","Integraci??n Econ??mica Internacional","Transporte Global","E-Commerce","Gerencia Comercial Multinacional","Operaciones Financieras Internacionales","Desarrollo de Proyectos Internacionales","International Supply Chain Management","Direcci??n Multinacional","Coyuntura Econ??mica Internacional"};
        String[] COD_ADM = {"AD144","AD170","AD213","AD180","AD183","AD184","AD186","AD207","AD187","AD204","AN78","AN97","AN22","AN79","AN20","AN96","AN80","AN81","AN58","AN82","AN48","AN84","AN85","AN86","AN28"};
        String[] NOM_ARQ = {"TI - Introducci??n al Dise??o","Expresi??n Art??stica y Espacial","TII - Arquitectura y Arte","Introducci??n a la Arquitectura","Dibujo Arquitect??nico","TIII - Arquitectura y Entorno","An??lisis Arquitect??nico","Arte y Arquitectura de la Antig??edad a la Edad","Modelaci??n Estructural I","TIV - Arquitectura y Funcionalidad","Sostenibilidad y Medio Ambiente","Arte y Arquitectura de la Edad Media al Renaci","Obras Preliminares","Modelaci??n Estructural II","Conocimiento del CAD","TV - Arquitectura y Medio Ambiente","Arte y Arquitectura del Barroco al Art Nouveau","Alba??iler??a","Instalaciones en Edificaciones","TVI - Arquitectura y Construcci??n","Arquitectura Peruana","Arte y Arquitectura Moderna y Contempor??nea","Techos Aligerados y Encofrados","Metodolog??a de la Investigaci??n","TVII - Taller de Integraci??n"};
        String[] COD_ARQ = {"AR305","AR287","AR334","AR01","AR351","AR307","AR335","AR336","AR337","AR308","AR338","AR339","AR340","AR341","AR342","AR309","AR343","AR344","AR293","AR313","AR110","AR345","AR346","AR347","AR310"};
        String[] NOM_BIL = {"Diversidad de la Vida en la Tierra","Biolog??a de los Microorganismos","Procesos Qu??micos en la Biolog??a","El Universo Celular","C??lulas, Tejidos y Bioim??genes","Procesos F??sicos en la Biolog??a","Biomol??culas","Herramientas Bioinform??ticas","T??cnicas para Ciencias Biol??gicas ","Comportamiento en el Grupo Profesional ","Principios de Programaci??n Bioinform??tica ","C??mo se Construye un Fenotipo","Ciencias y Tecnolog??as ??micas","Principios de la Farmacolog??a","Estructura y Funci??n de Prote??nas","T??cnicas Avanzadas para Ciencias Biol??gicas","Herramientas para la Gesti??n Profesional","Grandes Retos Biotecnol??gicos","Estructura y Funci??n Vegetal","Seminario Integrador A","Metodolog??a de la Investigaci??n Cient??fica","Grandes Retos en Biomedicina","Estructura y Funci??n Animal y Humana","Mol??culas y Salud P??blica","Fundamentos Moleculares de las Enfermedades"};
        String[] COD_BIL = {"BL04","BL05","BL06","BL07","BL08","BL09","BL10","BL11","BL12","BL13","BL14","BL15","BL16","BL17","BL18","BL19","BL20","BL21","BL22","BL23","BL24","BL25","BL26","BL27","BL28"};
        String[] NOM_COM = {"Historia del Cine","Teor??a de la Imagen y el Sonido ","Nuevos Medios y Nuevas Tendencias","Audiencias y Programaci??n ","Narrativa Audiovisual ","Lenguaje Audiovisual ","Soportes Tecnol??gicos","G??neros y Formatos","Gesti??n de la Producci??n Audiovisual e Interactiva","Taller de Realizaci??n Audiovisual","Taller de Gui??n Audiovisual ","Fundamentos de Expresi??n Musical","Direcci??n de Fotograf??a ","Taller de Dise??o Gr??fico I ","Taller de Televisi??n Interactiva ","Direcci??n Art??stica","Taller de Edici??n","Financiamiento y Marketing Audiovisual ","Taller de Programas de Entretenimiento ","Postproducci??n Audiovisual","Taller de Sonido y Musicalizaci??n","Proyectos Audiovisuales e Interactivos ","Taller de Documentales ","Taller de Producci??n y Realizaci??n de Programas Dramatizados","Taller De Direcci??n de Actores"};
        String[] COD_COM = {"AV01","AV00","AV02","AV36","AV35","AV06","AV54","AV05","AV12","AV32","AV37","AV56","AV38","CO22","AV49","AV15","AV39","AV13","AV40","AV41","AV42","AV43","AV50","AV44","AV45"};
        String[] NOM_DER = {"Derecho de las Personas","Econom??a Pol??tica","Instituciones del Derecho","Taller de Liderazgo I","Razonamiento e Investigaci??n Jur??dica","Responsabilidad Civil","Contabilidad para Abogados","Derechos de Propiedad","Fundamentos de la Contrataci??n I","Taller de Liderazgo II","Teor??a Constitucional y Pol??tica","Derecho Laboral","Derecho Societario y Corporativo","Derecho de Familia y Sucesiones","Derechos Fundamentales","Fundamentos de la Contrataci??n II","Organizaci??n del Estado","Contratos I","Derecho Administrativo","Garant??as","Ley Penal y Teor??a del Delito","Seminario de Negociaci??n y Conciliaci??n","Teor??a de los Tributos","Contratos II","Delitos Econ??micos y Empresariales"};
        String[] COD_DER = {"DE244","DE246","DE236","DE335","DE158","DE10","DE180","DE197","DE251","DE337","DE250","DE311","DE235","DE253","DE237","DE292","DE238","DE260","DE732","DE239","DE50","DE264","DE736","DE262","DE240"};
        String[] NOM_DIS = {"Proporciones Visuales","Dibujo de Interiores I","Procesos Creativos","Dibujo de Interiores II","Usuario y Respuesta","Expresi??n Digital","Apuntes y Perspectivas","Espacio Interior y H??bitat","Software Avanzado","Revestimiento, Textura y Color","Centro de Hospedaje","Aplicaci??n Digital","Construcci??n y Prototipo Final","Cerramientos y Acabados","Branding y Comercio Empresarial","Instalaci??n de Redes","Equipamiento Comercial","An??lisis e Identificaci??n de Marca","Local de Venta","Gesti??n y Liderazgo","Dise??o de Iluminaci??n","Inserci??n en Monumentos Hist??ricos","Patrimonio Hist??rico","Confort Ambiental y Tratamiento Ac??stico","Escenograf??a"};
        String[] COD_DIS = {"DI188","DI146","DI152","DI156","DI155","DI159","DI157","DI158","DI162","DI160","DI161","DI166","DI165","DI121","DI164","DI167","DI169","DI163","DI170","DI174","DI168","DI131","DI132","DI171","DI172"};
        String[] NOM_ECO = {"Econom??a P??blica","Escuela Austr??aca de Econom??a","Evaluaci??n de Proyectos","Microeconometr??a","Modelos y Mercados de Derivados","Organizaci??n Industrial","Instrumentos de Renta Fija","Regulaci??n Econ??mica","Teor??a Monetaria","Administraci??n del Riesgo","Crecimiento y Desarrollo","Macroeconom??a Abierta","Proyecto de Tesis I","Proyecto de Tesis II","Pol??tica Econ??mica","An??lisis de la Coyuntura Econ??mica","Historia del Pensamiento Econ??mico","Instrumentos de Renta Variable","Introducci??n a la Econometr??a","Teor??a Econ??mica Internacional","Macroeconom??a Aplicada","Historia Econ??mica del Per??","Finanzas Corporativas II","Microeconom??a Aplicada","Macroeconom??a Avanzada"};
        String[] COD_ECO = {"EF13","EF26","EF78","EF73","EF60","EF63","EF69","EF74","EF32","EF56","EF36","EF53","EF67","EF68","EF12","EF41","EF14","EF75","EF72","EF42","EF08","EF04","EF80","EF07","EF06"};
        String[] NOM_GAS = {"Antropolog??a y Psicolog??a de la Alimentaci??n y Gastronom??a","Introducci??n a los Negocios Gastron??micos","Principios de Nutrici??n y Diet??tica","Qu??mica de los Alimentos","Bases F??sicas y Fisioqu??micas de los Productos y Procesos Culinarios","Fundamentos T??cnicos de Pasteler??a","Insumos e Historia de la Gastronom??a del Per??","T??cnicas Culinarias B??sicas","Fundamentos de Log??stica de Alimentos y Bebidas","Industria Alimentaria","Pasteler??a Intermedia","T??cnicas Culinarias Intermedias","Planeamiento y Desarrollo del Men??","T??cnicas Culinarias Avanzadas","T??cnicas de Panader??a","Cocina Tradicional de Local a Global","Elaboraci??n y Cata de Vinos","Pasteler??a Avanzada","Costos y Presupuestos para la Gastronom??a","Marketing en Gastronom??a","Planificaci??n y Dise??os de Espacios Culinarios","Procesos de Innovaci??n de T??cnicas Culinarias","Producci??n Certificada y Marcas de Calidad","Colectividades e Industrias Afines","Gesti??n de la Restauraci??n"};
        String[] COD_GAS = {"GA01","GA42","GA111","GA03","GA117","GA118","GA46","GA119","GA48","GA49","GA120","GA121","GA104","GA122","GA123","GA125","GA124","GA126","GA64","GA67","GA105","GA127","GA66","GA114","GA113"};
        String[] NOM_CIV = {"Dibujo Asistido por el Computador","Introducci??n a la Ingenier??a Civil","Topograf??a","Materiales de Construcci??n","Est??tica","Tecnolog??a del Concreto","Construcci??n I","Din??mica","Ingenier??a de Carreteras","Mec??nica de Materiales","Mec??nica de Suelos","An??lisis Estructural I","Ingenier??a Geot??cnica","Introducci??n a los M??todos Computacionales","Mec??nica de Fluidos","Modelaci??n de Edificaciones","An??lisis Estructural II","Comportamiento y Dise??o en Concreto","Construcci??n II","Costos y Presupuestos","Hidr??ulica de Canales","Ingenier??a de Tr??nsito","Ingenier??a de los Recursos Hidr??ulicos","Planificaci??n y Control de Obras","Taller de Tesis"};
        String[] COD_CIV = {"CI161","CI160","CI556","CI164","CI119","CI557","CI560","CI558","CI559","CI168","CI561","CI562","CI563","CI564","CI565","CI566","CI567","CI568","CI569","CI570","CI572","CI571","CI573","CI575","CI574"};
        String[] NOM_AMB = {"Introducci??n a la Ingenier??a y Gesti??n Ambiental","Ecolog??a Aplicada","Geograf??a F??sica","Balance de Materia y Energ??a","Contaminaci??n y Control de la Calidad del Suelo","Fundamentos de Microbiolog??a Ambiental","Flujo de Fluidos","Gesti??n de Proyectos Ambientales","Gesti??n y An??lisis de Procesos Ecoeficientes (PML)","Negociaci??n y Manejo de Conflictos Socioambiental","Tecnolog??as para el Control de la Contaminaci??n Atmosf??rica","Evaluaci??n del Impacto Ambiental (EIA)","Gesti??n y Tratamiento de Residuos S??lidos","Operaciones y Procesos para el Tratamiento de Aguas Residuales","Responsabilidad Social Empresarial (ISO 26000, GRI)","Sistemas de Gesti??n Ambiental","Herramientas de Producci??n M??s Limpia","Negocios y Finanzas Ambientales","Salud Ambiental","Tecnolog??a de los Biocombustibles","Gesti??n y Gerencia de la Energ??a","Energ??as Limpias","Manejo y Gesti??n Amb. de Cuencas Hidrogr??ficas","Biotecnolog??a del Medio Ambiente","Ecodise??o y Marketing Ecol??gico"};
        String[] COD_AMB = {"IG00","IG37","IG39","IG11","IG10","IG01","IG16","IG14","IG12","IG15","IG13","IG19","IG18","IG17","IG21","IG20","IG22","IG28","IG24","IG25","IG26","IG27","IG38","IG29","IG31"};
        String[] NOM_BIO = {"Introducci??n a la Ingenier??a Biom??dica","Biomateriales","Biotecnolog??a","Instrumentaci??n Biom??dica","Bioelectricidad","Fundamentos de Biomec??nica","Fundamentos de Bioinform??tica","Dise??o de Dispositivos M??dicos","Modelamiento de Sistemas Fisiol??gicos","Biomec??nica Aplicada","Machine Learning para Bioinform??tica","Telemedicina y Telesalud","Proyecto Biom??dico 1 - Capstone","Proyecto Biom??dico 2 - Capstone","Gesti??n de Tecnolog??a en Salud","Gesti??n de Proyectos de Ingenier??a","Procesamiento Avanzado de Se??ales e Im??genes","Procesamiento Digital de Se??ales","Programaci??n Avanzada de Computadoras","Ingenier??a de Control 1","Se??ales y Sistemas","Electromagnetismo","Dispositivos y Circuitos Anal??gicos","An??lisis de Circuitos El??ctricos 2","F??sica II"};
        String[] COD_BIO = {"BO00","BO01","BO02","BO03","BO04","BO05","BO06","BO07","BO08","BO09","BO10","BO11","BO12","BO13","BO14","BO15","BO16","BO17","BO18","BO19","BO20","BO21","BO22","BO23","BO24"};
        String[] NOM_MEC = {"Dispositivos y Circuitos Anal??gicos","F??sica III","Matem??tica Anal??tica V","Microcontroladores","Dibujo de Ingenier??a II","Ingenier??a de Control I","Sensores y Actuadores","Se??ales y Sistemas","Termodin??mica Aplicada","Gesti??n en Proyectos de Ingenier??a","Ingenier??a de Control II","Mec??nica para Ingenieros","M??quinas El??ctricas","Operaciones Unitarias","Procesamiento Digital de Se??ales","Control de Procesos","Electr??nica Industrial","Procesamiento Avanzado de Se??ales e Im??genes","Seminario de Investigaci??n Acad??mica II (Ing)","Sistemas CAD/CAM","Manufactura Integrada por Computadora","Proyecto Mecatr??nico I","Sistemas de Automatizaci??n Industrial","Proyecto Mecatr??nico II","Rob??tica Industrial"};
        String[] COD_MEC = {"MC37","MC02","MC03","MC04","MC05","MC06","MC07","MC08","MC09","MC10","MC11","MC12","MC13","MC14","MC15","MC39","MC16","MC17","MC19","MC42","MC18","MC31","MC20","MC41","MC32"};
        String[] NOM_SOF = {"Especificaci??n y An??lisis de Requerimientos","Arquitectura de Computadoras y Sistemas Operativos","Dise??o de Base de Datos","IHC y Tecnolog??as M??viles","Aplicaciones Open Source","Aplicaciones Web","Dise??o y Patrones de Software","Evoluci??n de Software","Finanzas e Ingenier??a Econ??mica","Redes y Comunicaci??n de Datos","Dise??o de Experimentos de ISW","Fundamentos de Arquitectura de Software","Inteligencia Artificial","Gerencia de Proyectos de Software","Taller de Desempe??o Profesional","Calidad y Mejora de Procesos Software","Evaluaci??n y Nuevas Tendencias de Arquitectura de Software","Taller de Proyecto I","Fundamentos de Inteligencia de Negocios","Taller de Proyecto II","F??sica II","Matem??tica Computacional","C??lculo II","Contabilidad y Presupuestos","Organizaci??n y Direcci??n de Empresas"};
        String[] COD_SOF = {"SI397","SI643","SI400","SI385","SI652","SI653","SI424","SI431","SI642","SI640","SI656","SI657","SI404","SI647","SI639","SI438","SI678","SI644","SI649","SI646","SI999","SI998","SI997","SI996","SI995"};
        String[] NOM_ODO = {"Fundamentos de Odontolog??a I","Fundamentos de Odontolog??a II","Procesos Biol??gicos II","Sistema Estomatogn??tico I","Introducci??n al Diagn??stico Odontol??gico","Sistema Estomatogn??tico II","Integraci??n Cl??nica - Patol??gica I","Pr??ctica Precl??nica Odontol??gica I","Cl??nica Integral I","Integraci??n Cl??nica - Patol??gica II","Pr??ctica Precl??nica Odontol??gica II","Terap??utica Integrada","Cl??nica Integral II","Epidemiolog??a","Pr??ctica Precl??nica Odontol??gica III","Cl??nica Integral III","Metodolog??a de la Investigaci??n Cient??fica","Cl??nica Integral IV","Gesti??n Odontol??gica","Proyecto de Tesis I","Emprendedurismo I","Internado Cl??nico","Odontolog??a Comunitaria","Proyecto de Tesis II","Seminario Integrador I"};
        String[] COD_ODO = {"OD195","OD123","OD455","OD160","OD457","OD456","OD476","OD168","OD536","OD477","OD323","OD165","OD537","OD171","OD324","OD538","OD460","OD539","OD170","OD463","OD175","OD540","OD462","OD465","OD326"};
        String[] NOM_MED = {"Estructura y Funci??n","Fundamentos de la Salud","Procesos Biol??gicos I","Gesti??n y B??squeda de Informaci??n Cient??fica","Procesos Biol??gicos I","Pr??ctica M??dica: Sist. Nervioso y Tegumentario","Sistema Nervioso","Sistema Tegumentario","Agresi??n y Defensa","Farmacolog??a","Pr??ctica M??dica: Sistemas Circulatorio y Respiratorio","Razonamiento Cuantitativo","Sistema Circulatorio","Sistema Respiratorio","Inmunidad e Infecci??n","Pr??ctica M??dica: Sistemas Endocrino, Reproductor y Excretor","Sistemas Endocrino y Reproductor","Sistema Excretor","Estilos de Vida, Medio Ambiente y Salud","Sistema Digestivo","Sistema Hematopoy??tico","Sistema Locomotor","Cerebro y Comportamiento","Integraci??n Cl??nico-Patol??gica I","Mecanismos Gen??ticos de Enfermedad"};
        String[] COD_MED = {"ME129","ME137","ME206","ME108","ME207","ME139","ME140","ME141","ME142","ME143","ME146","MA653","ME144","ME145","ME208","ME149","ME148","ME150","ME151","ME154","ME153","ME152","ME158","ME157","ME156"};
        String[] NOM_MUS = {"Lectura y Entrenamiento Auditivo III","Armon??a I","Fundamentos F??sico-Musical","An??lisis Po??tico de Canciones","Taller de Instrumento II","Armon??a II","Contrapunto I","Estrategias de la Comunicaci??n","Fundamentos de la Producci??n Musical","Composici??n de Canciones I","Taller de Instrumento III","Temas de Historia Universal","Armon??a III","Contrapunto II","Aspectos Legales de la Industria Musical","Producci??n Musical Digital","Historia de la M??sica Occidental","Composici??n de Canciones II","Taller de Instrumento IV","An??lisis Musical I","Arreglos Musicales I","Principios Contables y Financieros","Tecnolog??a de Audio","M??sica Peruana","Ensamble de M??sica Peruana"};
        String[] COD_MUS = {"MS259","MS219","MS74","MS83","MS198","MS253","MS243","MS138","MS244","MS240","MS199","MS181","MS254","MS245","MS139","MS82","MS246","MS241","MS200","MS247","MS42","MS31","MS132","MS136","MS41"};
        String[] NOM_PSI = {"Bases Estruc. y Func. del Comportamiento Humano","Introducci??n a la Psicolog??a","Procesos Psicol??gicos B??sicos","Bases Biol??gicas del Comportamiento Humano","Bases de Evaluaci??n Psicol??gica","Desarrollo Humano en la Infancia y Adolescencia","Desarrollo Humano del Adulto y Adulto Mayor","Intervenciones Sociales y Comunitarias","Procesos Psicopat. en la Infancia y Adolescencia","Salud P??blica","T??cnicas e Instrumentos de Evaluaci??n Psicol??gica","Intervenciones en Contextos Educativos","Procesos de Evaluaci??n Psicol??gica","Psicopatolog??a del Adulto","Trabajo, Subjetividad y Salud Mental","Externado en Procesos Educativos","Farmacolog??a y Comportamiento Humano","Intervenciones en Contextos Laborales","Intervenciones en Salud","Psicolog??a de la Conducta","Externado en Procesos Sociales y Prom. De La Salud","Intervenciones Conductuales","Investigaci??n Cient??fica","Psicoan??lisis","Selecci??n Y Evaluaci??n De Personal"};
        String[] COD_PSI = {"PS391","PS372","PS371","PS373","PS240","PS374","PS242","PS376","PS392","PS245","PS375","PS377","PS379","PS378","PS248","PS384","PS383","PS381","PS380","PS252","PS396","PS382","PS394","PS254","PS283"};
        String[] NOM_REL = {"Introducci??n a las Relaciones Internacionales","Actores de las Relaciones Internacionales","Aspectos Jur??dicos en Relaciones Internacionales","Finanzas Globales","Estad??stica Aplicada en Relaciones Internacionales","Pol??ticas P??blicas","Derecho de los Tratados","Historia de las Relaciones Internacionales","Pol??tica Exterior Peruana","Sistemas Pol??ticos y Electorales Comparados","Teor??a de las Relaciones Internacionales I","M??todos Cualitativos y Cuantitativos","Nuevas Tecnolog??as Comunicacionales","Pol??tica Internacional en el Oriente Medio","Procesos de Integraci??n Latinoamericanos","Energ??as en el Mundo Contempor??neo","Pol??tica Internacional en ??frica","Protocolo y Ceremonial","Teor??a de las Relaciones Internacionales II","Comercio Internacional","Global Dilemmas","Estructura y Din??mica de la Sociedad Internacional","Pol??tica Internacional en Europa","Sistema Financiero y Monetario Internacional","Cooperaci??n Internacional y Desarrollo"};
        String[] COD_REL = {"RI03","RI06","RI04","RI05","RI08","RI07","RI13","RI09","RI10","RI12","RI11","RI16","RI14","RI15","RI17","RI19","RI21","RI20","RI18","RI26","RI24","RI22","RI23","RI25","RI30"};
        String[] NOM_TER = {"Fundamentos de Fisioterapia","Agresi??n y Defensa","Evaluaci??n y Diagn??stico Fisioterap??utico I","Sistema Locomotor","Sistema Tegumentario","Biomec??nica","Evaluaci??n y Diagn??stico Fisioterap??utico II","Sistema Endocrino y Reproductor","Sistema Nervioso","Fisioterapia del Aparato Locomotor I","Sistema Circulatorio","Sistema Respiratorio","Cl??nica Integral I","Desarrollo Psicomotor","Fisiolog??a del Ejercicio","Fisioterapia del Aparato Locomotor II","Integraci??n Cl??nico Farmacol??gica","Cl??nica Integral II","Fisioterapia Neurol??gica I","Fisioterapia Respiratoria y Cardiol??gica","Integraci??n Cl??nico Patol??gica","Metodolog??a de la Investigaci??n Cient??fica","Ergonom??a y Salud Ocupacional","Fisioterapia Dermatofuncional","Fisioterapia Neurol??gica II"};
        String[] COD_TER = {"TF162","TF121","TF163","TF83","TF124","TF151","TF164","TF122","TF123","TF84","TF125","TF126","TF152","TF89","TF153","TF87","TF165","TF154","TF155","TF156","TF166","TF167","TF157","TF158","TF159"};
        String[] NOM_TUR = {"Introducci??n al Turismo","Historia","Geograf??a Tur??stica","Patrimonio Tur??stico","Empresas Tur??sticas","Operaci??n de agencias de viajes","Turismo sostenible","E-commerce y turismo","Gesti??n del patrimonio natural","Gesti??n del patrimonio cultural","Planificaci??n tur??stica","Desarrollo tur??stico","Metodolog??a para la consultoria tur??stica","Desarrollo de destinos tur??sticos","Promoci??n tur??stica","Etica y Responsabilidad social empresarial","Proyectos de inversi??n p??blica en turismo","Fundamentos para el C??lculo","Fundamentos de la Gerencia","C??lculo","Fundamentos de negocios internacionales","Macroeconom??a","Teor??a microecon??mica","Contabilidad general","Administraci??n de las operaciones"};
        String[] COD_TUR = {"TU48","TU49","TU50","TU70","TU51","TU71","TU47","TU53","TU52","TU54","TU72","TU56","TU74","TU73","TU69","TU66","TU58","TU99","TU98","TU97","TU96","TU95","TU94","TU93","TU92"};

        if (CARR.equals("ELEC")){for (int i = 0; i<COD_ELE.length;i++){if (CURSO.equals(COD_ELE[i])){CLASES.add(NOM_ELE[i]);}}}
        else if (CARR.equals("ADMI")){for (int i = 0; i<COD_ADM.length;i++){if (CURSO.equals(COD_ADM[i])){CLASES.add(NOM_ADM[i]);}}}
        else if (CARR.equals("ARQI")){for (int i = 0; i<COD_ARQ.length;i++){if (CURSO.equals(COD_ARQ[i])){CLASES.add(NOM_ARQ[i]);}}}
        else if (CARR.equals("BIOL")){for (int i = 0; i<COD_BIL.length;i++){if (CURSO.equals(COD_BIL[i])){CLASES.add(NOM_BIL[i]);}}}
        else if (CARR.equals("COMU")){for (int i = 0; i<COD_COM.length;i++){if (CURSO.equals(COD_COM[i])){CLASES.add(NOM_COM[i]);}}}
        else if (CARR.equals("DERE")){for (int i = 0; i<COD_DER.length;i++){if (CURSO.equals(COD_DER[i])){CLASES.add(NOM_DER[i]);}}}
        else if (CARR.equals("DISE")){for (int i = 0; i<COD_DIS.length;i++){if (CURSO.equals(COD_DIS[i])){CLASES.add(NOM_DIS[i]);}}}
        else if (CARR.equals("ECON")){for (int i = 0; i<COD_ECO.length;i++){if (CURSO.equals(COD_ECO[i])){CLASES.add(NOM_ECO[i]);}}}
        else if (CARR.equals("GAST")){for (int i = 0; i<COD_GAS.length;i++){if (CURSO.equals(COD_GAS[i])){CLASES.add(NOM_GAS[i]);}}}
        else if (CARR.equals("CIVI")){for (int i = 0; i<COD_CIV.length;i++){if (CURSO.equals(COD_CIV[i])){CLASES.add(NOM_CIV[i]);}}}
        else if (CARR.equals("AMBI")){for (int i = 0; i<COD_AMB.length;i++){if (CURSO.equals(COD_AMB[i])){CLASES.add(NOM_AMB[i]);}}}
        else if (CARR.equals("BIOM")){for (int i = 0; i<COD_BIO.length;i++){if (CURSO.equals(COD_BIO[i])){CLASES.add(NOM_BIO[i]);}}}
        else if (CARR.equals("MECA")){for (int i = 0; i<COD_MEC.length;i++){if (CURSO.equals(COD_MEC[i])){CLASES.add(NOM_MEC[i]);}}}
        else if (CARR.equals("SOFT")){for (int i = 0; i<COD_SOF.length;i++){if (CURSO.equals(COD_SOF[i])){CLASES.add(NOM_SOF[i]);}}}
        else if (CARR.equals("MEDI")){for (int i = 0; i<COD_MED.length;i++){if (CURSO.equals(COD_MED[i])){CLASES.add(NOM_MED[i]);}}}
        else if (CARR.equals("MUSI")){for (int i = 0; i<COD_MUS.length;i++){if (CURSO.equals(COD_MUS[i])){CLASES.add(NOM_MUS[i]);}}}
        else if (CARR.equals("ODON")){for (int i = 0; i<COD_ODO.length;i++){if (CURSO.equals(COD_ODO[i])){CLASES.add(NOM_ODO[i]);}}}
        else if (CARR.equals("PSIC")){for (int i = 0; i<COD_PSI.length;i++){if (CURSO.equals(COD_PSI[i])){CLASES.add(NOM_PSI[i]);}}}
        else if (CARR.equals("RELA")){for (int i = 0; i<COD_REL.length;i++){if (CURSO.equals(COD_REL[i])){CLASES.add(NOM_REL[i]);}}}
        else if (CARR.equals("TERA")){for (int i = 0; i<COD_TER.length;i++){if (CURSO.equals(COD_TER[i])){CLASES.add(NOM_TER[i]);}}}
        else if (CARR.equals("TURI")){for (int i = 0; i<COD_TUR.length;i++){if (CURSO.equals(COD_TUR[i])){CLASES.add(NOM_TUR[i]);}}}
    }
}