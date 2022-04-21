package app.acsolutions.ventisysupc.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.platforminfo.DefaultUserAgentPublisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import app.acsolutions.ventisysupc.MENUS.MainActivity;
import app.acsolutions.ventisysupc.R;

public class AdminProgramarActivity extends AppCompatActivity implements View.OnClickListener {
    private Button BACK, PROGRAMAR, CAMPUS, CARRERA, CURSO, SECCION, FECHA, HORA, DURACION, DOCENTE;
    private AlertDialog.Builder OPCIONES;
    private CharSequence[] CAMP, CARR, CURS, SECC, DURA, DOCE, CCURS;
    private ProgressDialog PROGRESO;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String COD_CAMPUS, COD_CURSO, COD_CARRE, COD_HORA, COD_FECHA, COD_DOCEN, COD_SECCION, DATO;

    ArrayList<String> PROFESOR = new ArrayList<>();
    ArrayList<String> COD_PROF = new ArrayList<>();

    CharSequence[] CARRERAS = {"Administración", "Arquitectura", "Biología", "Comunicación", "Derecho", "Diseño", "Economía", "Gastronomía", "Ingeniería Civil", "Ingeniería Ambiental", "Ingeniería Biomédica", "Ingeniería Electrónica", "Ingeniería Mecatrónica", "Ingeniería de Software", "Medicina", "Música", "Odontología", "Psicología", "Relaciones Internacionales", "Terapia Física", "Turismo y Administración"};
    String[] CODIGOS_CAR = {"ADMI", "ARQI", "BIOL", "COMU", "DERE", "DISE", "ECON", "GAST", "CIVI", "AMBI", "BIOM", "ELEC", "MECA", "SOFT", "MEDI", "MUSI", "ODON", "PSIC", "RELA", "TERA", "TURI"};
    CharSequence[] CARRERAS_MO = {"Administración", "Arquitectura", "Comunicación", "Derecho", "Diseño", "Economía", "Gastronomía", "Ingeniería Civil", "Ingeniería Biomédica", "Ingeniería Electrónica", "Ingeniería Mecatrónica", "Ingeniería de Software", "Música", "Psicología", "Turismo y Administración"};
    String[] CODIGOS_CAR_MO = {"ADMI", "ARQI", "COMU", "DERE", "DISE", "ECON", "GAST", "CIVI", "BIOM", "ELEC", "MECA", "SOFT", "MUSI", "PSIC", "TURI"};
    CharSequence[] CARRERAS_VI = {"Administración", "Arquitectura", "Biología", "Comunicación", "Derecho", "Ingeniería Civil", "Ingeniería Ambiental", "Ingeniería de Software", "Medicina", "Música", "Odontología", "Psicología", "Terapia Física"};
    String[] CODIGOS_CAR_VI = {"ADMI", "ARQI", "BIOL", "COMU", "DERE", "CIVI", "AMBI", "SOFT", "MEDI", "MUSI", "ODON", "PSIC", "TERA"};
    CharSequence[] CARRERAS_SM = {"Administración", "Arquitectura", "Comunicación", "Derecho", "Ingeniería Civil", "Ingeniería Electrónica", "Ingeniería Mecatrónica", "Ingeniería de Software", "Psicología"};
    String[] CODIGOS_CAR_SM = {"ADMI", "ARQI", "COMU", "DERE", "CIVI", "ELEC", "MECA", "SOFT", "PSIC"};
    CharSequence[] CARRERAS_SI = {"Administración", "Comunicación", "Derecho", "Ingeniería Civil", "Ingeniería de Software", "Relaciones Internacionales"};
    String[] CODIGOS_CAR_SI = {"ADMI", "COMU", "DERE", "CIVI", "SOFT", "RELA"};
    CharSequence[] NOM_ELE = {"Introducción a la Electrónica", "Software para Ingeniería", "Circuitos Lógicos Digitales", "Redes De Comunicaciones 1", "Análisis de Circuitos Eléctricos 1", "Programación de Computadoras", "Sistemas Digitales", "Análisis de Circuitos Eléctricos 2", "Dispositivos y Circuitos Analógicos", "Microcontroladores", "Programación Avanzada de Computadoras", "Redes de Comunicaciones 2", "Sensores y Actuadores", "Señales y Sistemas", "Diseño de Circuitos Electrónicos", "Ingeniería de Comunicaciones", "Procesamiento Digital de Señales", "Procesamiento Avanzado de Señales e Imágenes", "Sistemas Embebidos", "Telecomunicaciones Digitales", "Proyecto Electrónico 1", "Robótica e Inteligencia Artificial", "Sistemas Operativos en Tiempo Real", "Sistemas de Automatización Industrial", "Proyecto Electrónico 2"};
    String[] COD_ELE = {"EL249", "EL227", "EL245", "EL190", "EL243", "EL172", "EL253", "EL244", "EL246", "EL174", "EL184", "EL229", "EL251", "EL231", "EL223", "EL217", "EL222", "EL228", "EL234", "EL186", "EL236", "EL109", "EL226", "EL252", "EL237"};
    CharSequence[] NOM_ADM = {"Fundamentos de la Gerencia","Diseño Organizacional y Procesos","Administración de Operaciones","Grandes Ideas en Gerencia","Business Intelligence & Predictability","Dirección Estratégica","Design Thinking","Estrategias de Negociación","Planificación Estratégica Aplicada","Emprendimiento de Negocios Sostenibles: Formulación","Introducción a los Negocios Internacionales","Inteligencia Comercial Internacional","Comercio Internacional","Plan Comercial Internacional","Aduanas","Costos y Cotizaciones Internacionales","Integración Económica Internacional","Transporte Global","E-Commerce","Gerencia Comercial Multinacional","Operaciones Financieras Internacionales","Desarrollo de Proyectos Internacionales","International Supply Chain Management","Dirección Multinacional","Coyuntura Económica Internacional"};
    String[] COD_ADM = {"AD144","AD170","AD213","AD180","AD183","AD184","AD186","AD207","AD187","AD204","AN78","AN97","AN22","AN79","AN20","AN96","AN80","AN81","AN58","AN82","AN48","AN84","AN85","AN86","AN28"};
    CharSequence[] NOM_ARQ= {"TI - Introducción al Diseño","Expresión Artística y Espacial","TII - Arquitectura y Arte","Introducción a la Arquitectura","Dibujo Arquitectónico","TIII - Arquitectura y Entorno","Análisis Arquitectónico","Arte y Arquitectura de la Antigüedad a la Edad","Modelación Estructural I","TIV - Arquitectura y Funcionalidad","Sostenibilidad y Medio Ambiente","Arte y Arquitectura de la Edad Media al Renaci","Obras Preliminares","Modelación Estructural II","Conocimiento del CAD","TV - Arquitectura y Medio Ambiente","Arte y Arquitectura del Barroco al Art Nouveau","Albañilería","Instalaciones en Edificaciones","TVI - Arquitectura y Construcción","Arquitectura Peruana","Arte y Arquitectura Moderna y Contemporánea","Techos Aligerados y Encofrados","Metodología de la Investigación","TVII - Taller de Integración"};
    String[] COD_ARQ = {"AR305","AR287","AR334","AR01","AR351","AR307","AR335","AR336","AR337","AR308","AR338","AR339","AR340","AR341","AR342","AR309","AR343","AR344","AR293","AR313","AR110","AR345","AR346","AR347","AR310"};
    CharSequence[] NOM_BIL = {"Diversidad de la Vida en la Tierra","Biología de los Microorganismos","Procesos Químicos en la Biología","El Universo Celular","Células, Tejidos y Bioimágenes","Procesos Físicos en la Biología","Biomoléculas","Herramientas Bioinformáticas","Técnicas para Ciencias Biológicas ","Comportamiento en el Grupo Profesional ","Principios de Programación Bioinformática ","Cómo se Construye un Fenotipo","Ciencias y Tecnologías Ómicas","Principios de la Farmacología","Estructura y Función de Proteínas","Técnicas Avanzadas para Ciencias Biológicas","Herramientas para la Gestión Profesional","Grandes Retos Biotecnológicos","Estructura y Función Vegetal","Seminario Integrador A","Metodología de la Investigación Científica","Grandes Retos en Biomedicina","Estructura y Función Animal y Humana","Moléculas y Salud Pública","Fundamentos Moleculares de las Enfermedades"};
    String[] COD_BIL = {"BL04","BL05","BL06","BL07","BL08","BL09","BL10","BL11","BL12","BL13","BL14","BL15","BL16","BL17","BL18","BL19","BL20","BL21","BL22","BL23","BL24","BL25","BL26","BL27","BL28"};
    CharSequence[] NOM_COM = {"Historia del Cine","Teoría de la Imagen y el Sonido ","Nuevos Medios y Nuevas Tendencias","Audiencias y Programación ","Narrativa Audiovisual ","Lenguaje Audiovisual ","Soportes Tecnológicos","Géneros y Formatos","Gestión de la Producción Audiovisual e Interactiva","Taller de Realización Audiovisual","Taller de Guión Audiovisual ","Fundamentos de Expresión Musical","Dirección de Fotografía ","Taller de Diseño Gráfico I ","Taller de Televisión Interactiva ","Dirección Artística","Taller de Edición","Financiamiento y Marketing Audiovisual ","Taller de Programas de Entretenimiento ","Postproducción Audiovisual","Taller de Sonido y Musicalización","Proyectos Audiovisuales e Interactivos ","Taller de Documentales ","Taller de Producción y Realización de Programas Dramatizados","Taller De Dirección de Actores"};
    String[] COD_COM = {"AV01","AV00","AV02","AV36","AV35","AV06","AV54","AV05","AV12","AV32","AV37","AV56","AV38","CO22","AV49","AV15","AV39","AV13","AV40","AV41","AV42","AV43","AV50","AV44","AV45"};
    CharSequence[] NOM_DER = {"Derecho de las Personas","Economía Política","Instituciones del Derecho","Taller de Liderazgo I","Razonamiento e Investigación Jurídica","Responsabilidad Civil","Contabilidad para Abogados","Derechos de Propiedad","Fundamentos de la Contratación I","Taller de Liderazgo II","Teoría Constitucional y Política","Derecho Laboral","Derecho Societario y Corporativo","Derecho de Familia y Sucesiones","Derechos Fundamentales","Fundamentos de la Contratación II","Organización del Estado","Contratos I","Derecho Administrativo","Garantías","Ley Penal y Teoría del Delito","Seminario de Negociación y Conciliación","Teoría de los Tributos","Contratos II","Delitos Económicos y Empresariales"};
    String[] COD_DER = {"DE244","DE246","DE236","DE335","DE158","DE10","DE180","DE197","DE251","DE337","DE250","DE311","DE235","DE253","DE237","DE292","DE238","DE260","DE732","DE239","DE50","DE264","DE736","DE262","DE240"};
    CharSequence[] NOM_DIS = {"Proporciones Visuales","Dibujo de Interiores I","Procesos Creativos","Dibujo de Interiores II","Usuario y Respuesta","Expresión Digital","Apuntes y Perspectivas","Espacio Interior y Hábitat","Software Avanzado","Revestimiento, Textura y Color","Centro de Hospedaje","Aplicación Digital","Construcción y Prototipo Final","Cerramientos y Acabados","Branding y Comercio Empresarial","Instalación de Redes","Equipamiento Comercial","Análisis e Identificación de Marca","Local de Venta","Gestión y Liderazgo","Diseño de Iluminación","Inserción en Monumentos Históricos","Patrimonio Histórico","Confort Ambiental y Tratamiento Acústico","Escenografía"};
    String[] COD_DIS = {"DI188","DI146","DI152","DI156","DI155","DI159","DI157","DI158","DI162","DI160","DI161","DI166","DI165","DI121","DI164","DI167","DI169","DI163","DI170","DI174","DI168","DI131","DI132","DI171","DI172"};
    CharSequence[] NOM_ECO = {"Economía Pública","Escuela Austríaca de Economía","Evaluación de Proyectos","Microeconometría","Modelos y Mercados de Derivados","Organización Industrial","Instrumentos de Renta Fija","Regulación Económica","Teoría Monetaria","Administración del Riesgo","Crecimiento y Desarrollo","Macroeconomía Abierta","Proyecto de Tesis I","Proyecto de Tesis II","Política Económica","Análisis de la Coyuntura Económica","Historia del Pensamiento Económico","Instrumentos de Renta Variable","Introducción a la Econometría","Teoría Económica Internacional","Macroeconomía Aplicada","Historia Económica del Perú","Finanzas Corporativas II","Microeconomía Aplicada","Macroeconomía Avanzada"};
    String[] COD_ECO = {"EF13","EF26","EF78","EF73","EF60","EF63","EF69","EF74","EF32","EF56","EF36","EF53","EF67","EF68","EF12","EF41","EF14","EF75","EF72","EF42","EF08","EF04","EF80","EF07","EF06"};
    CharSequence[] NOM_GAS = {"Antropología y Psicología de la Alimentación y Gastronomía","Introducción a los Negocios Gastronómicos","Principios de Nutrición y Dietética","Química de los Alimentos","Bases Físicas y Fisioquímicas de los Productos y Procesos Culinarios","Fundamentos Técnicos de Pastelería","Insumos e Historia de la Gastronomía del Perú","Técnicas Culinarias Básicas","Fundamentos de Logística de Alimentos y Bebidas","Industria Alimentaria","Pastelería Intermedia","Técnicas Culinarias Intermedias","Planeamiento y Desarrollo del Menú","Técnicas Culinarias Avanzadas","Técnicas de Panadería","Cocina Tradicional de Local a Global","Elaboración y Cata de Vinos","Pastelería Avanzada","Costos y Presupuestos para la Gastronomía","Marketing en Gastronomía","Planificación y Diseños de Espacios Culinarios","Procesos de Innovación de Técnicas Culinarias","Producción Certificada y Marcas de Calidad","Colectividades e Industrias Afines","Gestión de la Restauración"};
    String[] COD_GAS = {"GA01","GA42","GA111","GA03","GA117","GA118","GA46","GA119","GA48","GA49","GA120","GA121","GA104","GA122","GA123","GA125","GA124","GA126","GA64","GA67","GA105","GA127","GA66","GA114","GA113"};
    CharSequence[] NOM_CIV = {"Dibujo Asistido por el Computador","Introducción a la Ingeniería Civil","Topografía","Materiales de Construcción","Estática","Tecnología del Concreto","Construcción I","Dinámica","Ingeniería de Carreteras","Mecánica de Materiales","Mecánica de Suelos","Análisis Estructural I","Ingeniería Geotécnica","Introducción a los Métodos Computacionales","Mecánica de Fluidos","Modelación de Edificaciones","Análisis Estructural II","Comportamiento y Diseño en Concreto","Construcción II","Costos y Presupuestos","Hidráulica de Canales","Ingeniería de Tránsito","Ingeniería de los Recursos Hidráulicos","Planificación y Control de Obras","Taller de Tesis"};
    String[] COD_CIV = {"CI161","CI160","CI556","CI164","CI119","CI557","CI560","CI558","CI559","CI168","CI561","CI562","CI563","CI564","CI565","CI566","CI567","CI568","CI569","CI570","CI572","CI571","CI573","CI575","CI574"};
    CharSequence[] NOM_AMB = {"Introducción a la Ingeniería y Gestión Ambiental","Ecología Aplicada","Geografía Física","Balance de Materia y Energía","Contaminación y Control de la Calidad del Suelo","Fundamentos de Microbiología Ambiental","Flujo de Fluidos","Gestión de Proyectos Ambientales","Gestión y Análisis de Procesos Ecoeficientes (PML)","Negociación y Manejo de Conflictos Socioambiental","Tecnologías para el Control de la Contaminación Atmosférica","Evaluación del Impacto Ambiental (EIA)","Gestión y Tratamiento de Residuos Sólidos","Operaciones y Procesos para el Tratamiento de Aguas Residuales","Responsabilidad Social Empresarial (ISO 26000, GRI)","Sistemas de Gestión Ambiental","Herramientas de Producción Más Limpia","Negocios y Finanzas Ambientales","Salud Ambiental","Tecnología de los Biocombustibles","Gestión y Gerencia de la Energía","Energías Limpias","Manejo y Gestión Amb. de Cuencas Hidrográficas","Biotecnología del Medio Ambiente","Ecodiseño y Marketing Ecológico"};
    String[] COD_AMB = {"IG00","IG37","IG39","IG11","IG10","IG01","IG16","IG14","IG12","IG15","IG13","IG19","IG18","IG17","IG21","IG20","IG22","IG28","IG24","IG25","IG26","IG27","IG38","IG29","IG31"};
    CharSequence[] NOM_BIO = {"Introducción a la Ingeniería Biomédica","Biomateriales","Biotecnología","Instrumentación Biomédica","Bioelectricidad","Fundamentos de Biomecánica","Fundamentos de Bioinformática","Diseño de Dispositivos Médicos","Modelamiento de Sistemas Fisiológicos","Biomecánica Aplicada","Machine Learning para Bioinformática","Telemedicina y Telesalud","Proyecto Biomédico 1 - Capstone","Proyecto Biomédico 2 - Capstone","Gestión de Tecnología en Salud","Gestión de Proyectos de Ingeniería","Procesamiento Avanzado de Señales e Imágenes","Procesamiento Digital de Señales","Programación Avanzada de Computadoras","Ingeniería de Control 1","Señales y Sistemas","Electromagnetismo","Dispositivos y Circuitos Analógicos","Análisis de Circuitos Eléctricos 2","Física II"};
    String[] COD_BIO = {"BO00","BO01","BO02","BO03","BO04","BO05","BO06","BO07","BO08","BO09","BO10","BO11","BO12","BO13","BO14","BO15","BO16","BO17","BO18","BO19","BO20","BO21","BO22","BO23","BO24"};
    CharSequence[] NOM_MEC = {"Dispositivos y Circuitos Analógicos","Física III","Matemática Analítica V","Microcontroladores","Dibujo de Ingeniería II","Ingeniería de Control I","Sensores y Actuadores","Señales y Sistemas","Termodinámica Aplicada","Gestión en Proyectos de Ingeniería","Ingeniería de Control II","Mecánica para Ingenieros","Máquinas Eléctricas","Operaciones Unitarias","Procesamiento Digital de Señales","Control de Procesos","Electrónica Industrial","Procesamiento Avanzado de Señales e Imágenes","Seminario de Investigación Académica II (Ing)","Sistemas CAD/CAM","Manufactura Integrada por Computadora","Proyecto Mecatrónico I","Sistemas de Automatización Industrial","Proyecto Mecatrónico II","Robótica Industrial"};
    String[] COD_MEC = {"MC37","MC02","MC03","MC04","MC05","MC06","MC07","MC08","MC09","MC10","MC11","MC12","MC13","MC14","MC15","MC39","MC16","MC17","MC19","MC42","MC18","MC31","MC20","MC41","MC32"};
    CharSequence[] NOM_SOF = {"Especificación y Análisis de Requerimientos","Arquitectura de Computadoras y Sistemas Operativos","Diseño de Base de Datos","IHC y Tecnologías Móviles","Aplicaciones Open Source","Aplicaciones Web","Diseño y Patrones de Software","Evolución de Software","Finanzas e Ingeniería Económica","Redes y Comunicación de Datos","Diseño de Experimentos de ISW","Fundamentos de Arquitectura de Software","Inteligencia Artificial","Gerencia de Proyectos de Software","Taller de Desempeño Profesional","Calidad y Mejora de Procesos Software","Evaluación y Nuevas Tendencias de Arquitectura de Software","Taller de Proyecto I","Fundamentos de Inteligencia de Negocios","Taller de Proyecto II","Física II","Matemática Computacional","Cálculo II","Contabilidad y Presupuestos","Organización y Dirección de Empresas"};
    String[] COD_SOF = {"SI397","SI643","SI400","SI385","SI652","SI653","SI424","SI431","SI642","SI640","SI656","SI657","SI404","SI647","SI639","SI438","SI678","SI644","SI649","SI646","SI999","SI998","SI997","SI996","SI995"};
    CharSequence[] NOM_ODO = {"Fundamentos de Odontología I","Fundamentos de Odontología II","Procesos Biológicos II","Sistema Estomatognático I","Introducción al Diagnóstico Odontológico","Sistema Estomatognático II","Integración Clínica - Patológica I","Práctica Preclínica Odontológica I","Clínica Integral I","Integración Clínica - Patológica II","Práctica Preclínica Odontológica II","Terapéutica Integrada","Clínica Integral II","Epidemiología","Práctica Preclínica Odontológica III","Clínica Integral III","Metodología de la Investigación Científica","Clínica Integral IV","Gestión Odontológica","Proyecto de Tesis I","Emprendedurismo I","Internado Clínico","Odontología Comunitaria","Proyecto de Tesis II","Seminario Integrador I"};
    String[] COD_ODO = {"OD195","OD123","OD455","OD160","OD457","OD456","OD476","OD168","OD536","OD477","OD323","OD165","OD537","OD171","OD324","OD538","OD460","OD539","OD170","OD463","OD175","OD540","OD462","OD465","OD326"};
    CharSequence[] NOM_MED = {"Estructura y Función","Fundamentos de la Salud","Procesos Biológicos I","Gestión y Búsqueda de Información Científica","Procesos Biológicos I","Práctica Médica: Sist. Nervioso y Tegumentario","Sistema Nervioso","Sistema Tegumentario","Agresión y Defensa","Farmacología","Práctica Médica: Sistemas Circulatorio y Respiratorio","Razonamiento Cuantitativo","Sistema Circulatorio","Sistema Respiratorio","Inmunidad e Infección","Práctica Médica: Sistemas Endocrino, Reproductor y Excretor","Sistemas Endocrino y Reproductor","Sistema Excretor","Estilos de Vida, Medio Ambiente y Salud","Sistema Digestivo","Sistema Hematopoyético","Sistema Locomotor","Cerebro y Comportamiento","Integración Clínico-Patológica I","Mecanismos Genéticos de Enfermedad"};
    String[] COD_MED = {"ME129","ME137","ME206","ME108","ME207","ME139","ME140","ME141","ME142","ME143","ME146","MA653","ME144","ME145","ME208","ME149","ME148","ME150","ME151","ME154","ME153","ME152","ME158","ME157","ME156"};
    CharSequence[] NOM_MUS = {"Lectura y Entrenamiento Auditivo III","Armonía I","Fundamentos Físico-Musical","Análisis Poético de Canciones","Taller de Instrumento II","Armonía II","Contrapunto I","Estrategias de la Comunicación","Fundamentos de la Producción Musical","Composición de Canciones I","Taller de Instrumento III","Temas de Historia Universal","Armonía III","Contrapunto II","Aspectos Legales de la Industria Musical","Producción Musical Digital","Historia de la Música Occidental","Composición de Canciones II","Taller de Instrumento IV","Análisis Musical I","Arreglos Musicales I","Principios Contables y Financieros","Tecnología de Audio","Música Peruana","Ensamble de Música Peruana"};
    String[] COD_MUS = {"MS259","MS219","MS74","MS83","MS198","MS253","MS243","MS138","MS244","MS240","MS199","MS181","MS254","MS245","MS139","MS82","MS246","MS241","MS200","MS247","MS42","MS31","MS132","MS136","MS41"};
    CharSequence[] NOM_PSI = {"Bases Estruc. y Func. del Comportamiento Humano","Introducción a la Psicología","Procesos Psicológicos Básicos","Bases Biológicas del Comportamiento Humano","Bases de Evaluación Psicológica","Desarrollo Humano en la Infancia y Adolescencia","Desarrollo Humano del Adulto y Adulto Mayor","Intervenciones Sociales y Comunitarias","Procesos Psicopat. en la Infancia y Adolescencia","Salud Pública","Técnicas e Instrumentos de Evaluación Psicológica","Intervenciones en Contextos Educativos","Procesos de Evaluación Psicológica","Psicopatología del Adulto","Trabajo, Subjetividad y Salud Mental","Externado en Procesos Educativos","Farmacología y Comportamiento Humano","Intervenciones en Contextos Laborales","Intervenciones en Salud","Psicología de la Conducta","Externado en Procesos Sociales y Prom. De La Salud","Intervenciones Conductuales","Investigación Científica","Psicoanálisis","Selección Y Evaluación De Personal"};
    String[] COD_PSI = {"PS391","PS372","PS371","PS373","PS240","PS374","PS242","PS376","PS392","PS245","PS375","PS377","PS379","PS378","PS248","PS384","PS383","PS381","PS380","PS252","PS396","PS382","PS394","PS254","PS283"};
    CharSequence[] NOM_REL = {"Introducción a las Relaciones Internacionales","Actores de las Relaciones Internacionales","Aspectos Jurídicos en Relaciones Internacionales","Finanzas Globales","Estadística Aplicada en Relaciones Internacionales","Políticas Públicas","Derecho de los Tratados","Historia de las Relaciones Internacionales","Política Exterior Peruana","Sistemas Políticos y Electorales Comparados","Teoría de las Relaciones Internacionales I","Métodos Cualitativos y Cuantitativos","Nuevas Tecnologías Comunicacionales","Política Internacional en el Oriente Medio","Procesos de Integración Latinoamericanos","Energías en el Mundo Contemporáneo","Política Internacional en África","Protocolo y Ceremonial","Teoría de las Relaciones Internacionales II","Comercio Internacional","Global Dilemmas","Estructura y Dinámica de la Sociedad Internacional","Política Internacional en Europa","Sistema Financiero y Monetario Internacional","Cooperación Internacional y Desarrollo"};
    String[] COD_REL = {"RI03","RI06","RI04","RI05","RI08","RI07","RI13","RI09","RI10","RI12","RI11","RI16","RI14","RI15","RI17","RI19","RI21","RI20","RI18","RI26","RI24","RI22","RI23","RI25","RI30"};
    CharSequence[] NOM_TER = {"Fundamentos de Fisioterapia","Agresión y Defensa","Evaluación y Diagnóstico Fisioterapéutico I","Sistema Locomotor","Sistema Tegumentario","Biomecánica","Evaluación y Diagnóstico Fisioterapéutico II","Sistema Endocrino y Reproductor","Sistema Nervioso","Fisioterapia del Aparato Locomotor I","Sistema Circulatorio","Sistema Respiratorio","Clínica Integral I","Desarrollo Psicomotor","Fisiología del Ejercicio","Fisioterapia del Aparato Locomotor II","Integración Clínico Farmacológica","Clínica Integral II","Fisioterapia Neurológica I","Fisioterapia Respiratoria y Cardiológica","Integración Clínico Patológica","Metodología de la Investigación Científica","Ergonomía y Salud Ocupacional","Fisioterapia Dermatofuncional","Fisioterapia Neurológica II"};
    String[] COD_TER = {"TF162","TF121","TF163","TF83","TF124","TF151","TF164","TF122","TF123","TF84","TF125","TF126","TF152","TF89","TF153","TF87","TF165","TF154","TF155","TF156","TF166","TF167","TF157","TF158","TF159"};
    CharSequence[] NOM_TUR = {"Introducción al Turismo","Historia","Geografía Turística","Patrimonio Turístico","Empresas Turísticas","Operación de agencias de viajes","Turismo sostenible","E-commerce y turismo","Gestión del patrimonio natural","Gestión del patrimonio cultural","Planificación turística","Desarrollo turístico","Metodología para la consultoria turística","Desarrollo de destinos turísticos","Promoción turística","Etica y Responsabilidad social empresarial","Proyectos de inversión pública en turismo","Fundamentos para el Cálculo","Fundamentos de la Gerencia","Cálculo","Fundamentos de negocios internacionales","Macroeconomía","Teoría microeconómica","Contabilidad general","Administración de las operaciones"};
    String[] COD_TUR = {"TU48","TU49","TU50","TU70","TU51","TU71","TU47","TU53","TU52","TU54","TU72","TU56","TU74","TU73","TU69","TU66","TU58","TU99","TU98","TU97","TU96","TU95","TU94","TU93","TU92"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_programar);
        BACK = findViewById(R.id.back);
        CAMPUS = findViewById(R.id.campus);
        CARRERA = findViewById(R.id.carrera);
        CURSO = findViewById(R.id.curso);
        SECCION = findViewById(R.id.seccion);
        FECHA = findViewById(R.id.fecha);
        HORA = findViewById(R.id.hora);
        DURACION = findViewById(R.id.duracion);
        DOCENTE = findViewById(R.id.docente);
        PROGRAMAR = findViewById(R.id.programar);
        OPCIONES = new AlertDialog.Builder(this);
        CAMP = new CharSequence[]{"MONTERRICO","SAN ISIDRO","SAN MIGUEL","VILLA"};
        DURA = new CharSequence[]{"1","2","3","4","5"};
        CARR = new CharSequence[]{};
        CURS = new CharSequence[]{};
        SECC = new CharSequence[]{};
        DOCE = new CharSequence[]{};
        COD_CAMPUS = "";
        COD_CURSO = "";
        COD_CARRE = "";
        COD_HORA = "";
        COD_FECHA = "";
        COD_DOCEN = "";
        COD_SECCION = "";
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }
        else if (view.getId() == CAMPUS.getId()){
            OPCIONES.setTitle("Seleccionar Una Carrera:");
            OPCIONES.setItems(CAMP, (dialog, which) -> {
                CAMPUS.setText(CAMP[which].toString());
                if (CAMP[which].toString().equals("MONTERRICO")){ CARR = CARRERAS_MO;COD_CAMPUS = "MO"; }
                else if (CAMP[which].toString().equals("SAN ISIDRO")){ CARR = CARRERAS_SI;COD_CAMPUS = "SI"; }
                else if (CAMP[which].toString().equals("SAN MIGUEL")){ CARR = CARRERAS_SM;COD_CAMPUS = "SM"; }
                else if (CAMP[which].toString().equals("VILLA")){ CARR = CARRERAS_VI;COD_CAMPUS = "VI"; }
                CURS = new CharSequence[]{};
                CCURS = new CharSequence[]{};
                SECC = new CharSequence[]{};
                DOCE = new CharSequence[]{};
                CARRERA.setText("SELECCIONAR ...");
                CURSO.setText("SELECCIONAR ...");
                SECCION.setText("SELECCIONAR ...");
                DOCENTE.setText("SELECCIONAR ...");
                COD_CARRE = "";
                COD_CURSO = "";
                COD_SECCION = "";
                COD_DOCEN = "";
                String p = COD_CAMPUS+"_"+COD_CARRE+"_"+COD_CURSO+"_"+COD_SECCION+"_"+COD_FECHA+"_"+COD_HORA+"_"+COD_DOCEN;
                Log.e("STRING: ",p );
            });OPCIONES.show();
        }
        else if (view.getId() == CARRERA.getId()){
            OPCIONES.setTitle("Seleccionar Una Carrera:");
            OPCIONES.setItems(CARR, (dialog, which) -> {
                CARRERA.setText(CARR[which].toString());
                if (CARR[which].toString().equals("Administración")) {
                    CURS = NOM_ADM;
                    CCURS = COD_ADM;
                }
                else if (CARR[which].toString().equals("Arquitectura")) {
                    CURS = NOM_ARQ;
                    CCURS = COD_ARQ;
                }
                else if (CARR[which].toString().equals("Biología")) {
                    CURS = NOM_BIL;
                    CCURS = COD_BIL;
                }
                else if (CARR[which].toString().equals("Comunicación")) {
                    CURS = NOM_COM;
                    CCURS = COD_COM;
                }
                else if (CARR[which].toString().equals("Derecho")) {
                    CURS = NOM_DER;
                    CCURS = COD_DER;
                }
                else if (CARR[which].toString().equals("Diseño")) {
                    CURS = NOM_DIS;
                    CCURS = COD_DIS;
                }
                else if (CARR[which].toString().equals("Economía")) {
                    CURS = NOM_ECO;
                    CCURS = COD_ECO;
                }
                else if (CARR[which].toString().equals("Gastronomía")) {
                    CURS = NOM_GAS;
                    CCURS = COD_GAS;
                }
                else if (CARR[which].toString().equals("Ingeniería Civil")) {
                    CURS = NOM_CIV;
                    CCURS = COD_CIV;
                }
                else if (CARR[which].toString().equals("Ingeniería Ambiental")) {
                    CURS = NOM_AMB;
                    CCURS = COD_AMB;
                }
                else if (CARR[which].toString().equals("Ingeniería Biomédica")) {
                    CURS = NOM_BIO;
                    CCURS = COD_BIO;
                }
                else if (CARR[which].toString().equals("Ingeniería Electrónica")) {
                    CURS = NOM_ELE;
                    CCURS = COD_ELE;
                }
                else if (CARR[which].toString().equals("Ingeniería Mecatrónica")) {
                    CURS = NOM_MEC;
                    CCURS = COD_MEC;
                }
                else if (CARR[which].toString().equals("Ingeniería de Software")) {
                    CURS = NOM_SOF;
                    CCURS = COD_SOF;
                }
                else if (CARR[which].toString().equals("Medicina")) {
                    CURS = NOM_MED;
                    CCURS = COD_MED;
                }
                else if (CARR[which].toString().equals("Música")) {
                    CURS = NOM_MUS;
                    CCURS = COD_MUS;
                }
                else if (CARR[which].toString().equals("Odontología")) {
                    CURS = NOM_ODO;
                    CCURS = COD_ODO;
                }
                else if (CARR[which].toString().equals("Psicología")) {
                    CURS = NOM_PSI;
                    CCURS = COD_PSI;
                }
                else if (CARR[which].toString().equals("Relaciones Internacionales")) {
                    CURS = NOM_REL;
                    CCURS = COD_REL;
                }
                else if (CARR[which].toString().equals("Terapia Física")) {
                    CURS = NOM_TER;
                    CCURS = COD_TER;
                }
                else if (CARR[which].toString().equals("Turismo y Administración")) {
                    CURS = NOM_TUR;
                    CCURS = COD_TUR;
                }
                switch (COD_CAMPUS) {
                    case "MO":
                        COD_CARRE = CODIGOS_CAR_MO[which]; break;
                    case "SI":
                        COD_CARRE = CODIGOS_CAR_SI[which]; break;
                    case "SM":
                        COD_CARRE = CODIGOS_CAR_SM[which]; break;
                    case "VI":
                        COD_CARRE = CODIGOS_CAR_VI[which]; break;
                }
                SECC = new CharSequence[]{};
                DOCE = new CharSequence[]{};
                CURSO.setText("SELECCIONAR ...");
                SECCION.setText("SELECCIONAR ...");
                DOCENTE.setText("SELECCIONAR ...");
                COD_CURSO = "";
                COD_SECCION = "";
                COD_DOCEN = "";
                String p = COD_CAMPUS+"_"+COD_CARRE+"_"+COD_CURSO+"_"+COD_SECCION+"_"+COD_FECHA+"_"+COD_HORA+"_"+COD_DOCEN;
                Log.e("STRING: ",p );
            });OPCIONES.show();
        }
        else if (view.getId() == CURSO.getId()){
            OPCIONES.setTitle("Seleccionar Un Curso:");
            OPCIONES.setItems(CURS, (dialog, which) -> {
                CURSO.setText(CURS[which].toString());
                COD_CURSO = CCURS[which].toString();
                String PREFIJO = COD_CARRE.substring(0,2);
                SECC = new CharSequence[]{(PREFIJO+"85"),(PREFIJO+"99"),(PREFIJO+"98"),(PREFIJO+"75")};
                DOCE = new CharSequence[]{};
                SECCION.setText("SELECCIONAR ...");
                DOCENTE.setText("SELECCIONAR ...");
                COD_SECCION = "";
                COD_DOCEN = "";
                String p = COD_CAMPUS+"_"+COD_CARRE+"_"+COD_CURSO+"_"+COD_SECCION+"_"+COD_FECHA+"_"+COD_HORA+"_"+COD_DOCEN;
                Log.e("STRING: ",p );
            });OPCIONES.show();
        }
        else if (view.getId() == SECCION.getId()){
            OPCIONES.setTitle("Seleccionar Una Sección:");
            OPCIONES.setItems(SECC, (dialog, which) -> {
                SECCION.setText(SECC[which].toString());
                COD_SECCION = SECC[which].toString();
                DOCENTE.setText("SELECCIONAR ...");
                COD_DOCEN = "";
                String p = COD_CAMPUS+"_"+COD_CARRE+"_"+COD_CURSO+"_"+COD_SECCION+"_"+COD_FECHA+"_"+COD_HORA+"_"+COD_DOCEN;
                Log.e("STRING: ",p );
                PROGRESO = ProgressDialog.show(this,"¡Cargando!", "Por favor espere...",false,false);
                Log.e("CODIGO CURSO: ",COD_CARRE);
                db.collection("docentes").document(COD_CARRE)
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (Objects.requireNonNull(document).exists()) {
                                DATO = Objects.requireNonNull(document.getData()).toString();
                                DATO = DATO.replace("{", "");
                                DATO = DATO.replace("}", "");
                                String[] DATOS = DATO.split(",");
                                for (String s : DATOS) {
                                    String[] COSO = s.split("=");
                                    COD_PROF.add(COSO[0].replace(" ",""));
                                    PROFESOR.add(COSO[1]);
                                }
                                Log.e("COD: ",COD_PROF.toString());
                                Log.e("PRO: ",PROFESOR.toString());
                                DOCE = PROFESOR.toArray(new CharSequence[0]);
                                Log.e("COD: ", Arrays.toString(DOCE));
                                PROGRESO.dismiss();
                            }
                            else {
                                PROGRESO.dismiss();
                                Toast.makeText(AdminProgramarActivity.this, "No existe documento", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            PROGRESO.dismiss();
                            Toast.makeText(AdminProgramarActivity.this, "No se puso conectar satisfactoriamente " +  task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            });OPCIONES.show();
        }
        else if (view.getId() == FECHA.getId()){
            final Calendar C = Calendar.getInstance();
            int YEAR = C.get(Calendar.YEAR);
            int MONTH = C.get(Calendar.MONTH);
            int DAY = C.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog DTP = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker datePicker, int YEAR_, int MONTH_, int DAY_) {
                    FECHA.setText("FECHA: " + DAY_ + "/" + (MONTH_+1) + "/" + YEAR_ );
                    COD_FECHA =  ""+DAY_+(MONTH_+1)+YEAR_;
                    String p = COD_CAMPUS+"_"+COD_CARRE+"_"+COD_CURSO+"_"+COD_SECCION+"_"+COD_FECHA+"_"+COD_HORA+"_"+COD_DOCEN;
                    Log.e("STRING: ",p );
                }
            },YEAR,MONTH,DAY);
            DTP.show();
        }
        else if (view.getId() == HORA.getId()){
            final Calendar C = Calendar.getInstance();
            int HORARIO = C.get(Calendar.HOUR);
            int MINUTO = C.get(Calendar.MINUTE);
            TimePickerDialog TPD = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int HORA_, int MINUTO_) {
                    String MIMI = "";
                    if (MINUTO_ == 0){ MIMI = "00"; }
                    else if (MINUTO_ < 10){ MIMI = "0"+MINUTO_; }
                    else{ MIMI = ""+MINUTO_; }
                    String HOHO = "";
                    if (HORA_ == 0){ HOHO = "00"; }
                    else if (HORA_ < 10){ HOHO = "0"+HORA_; }
                    else{ HOHO = ""+HORA_; }
                    HORA.setText("HORA: " + HOHO + ":" + MIMI);
                    COD_HORA = HOHO+MIMI;
                    String p = COD_CAMPUS+"_"+COD_CARRE+"_"+COD_CURSO+"_"+COD_SECCION+"_"+COD_FECHA+"_"+COD_HORA+"_"+COD_DOCEN;
                    Log.e("STRING: ",p );
                }
            },HORARIO,MINUTO,true);
            TPD.show();
        }
        else if (view.getId() == DURACION.getId()){
            OPCIONES.setTitle("Seleccionar Duración:");
            OPCIONES.setItems(DURA, (dialog, which) -> {
                DURACION.setText("Duración: " + DURA[which].toString() + " HORA(S)");
            });OPCIONES.show();
        }
        else if (view.getId() == DOCENTE.getId()){
            OPCIONES.setTitle("Seleccionar Un Docente:");
            OPCIONES.setItems(DOCE, (dialog, which) -> {
                DOCENTE.setText(DOCE[which].toString());
                COD_DOCEN = COD_PROF.get(which);
                String p = COD_CAMPUS+"_"+COD_CARRE+"_"+COD_CURSO+"_"+COD_SECCION+"_"+COD_FECHA+"_"+COD_HORA+"_"+COD_DOCEN;
                Log.e("STRING: ",p );
            });OPCIONES.show();
        }
        else if (view.getId() == PROGRAMAR.getId()){
            PROGRESO = ProgressDialog.show(this,"¡Cargando!", "Por favor espere...",false,false);
            if (!CAMPUS.getText().toString().equals("SELECCIONAR ...")){
                if(!CARRERA.getText().toString().equals("SELECCIONAR ...")){
                    if (!CURSO.getText().toString().equals("SELECCIONAR ...")){
                        if (!SECCION.getText().toString().equals("SELECCIONAR ...")){
                            if (!FECHA.getText().toString().equals("FECHA")){
                                if (!HORA.getText().toString().equals("HORA INICIO")){
                                    if (!DURACION.getText().toString().equals("DURACIÓN")){
                                        if (!DOCENTE.getText().toString().equals("SELECCIONAR ...")){
                                            String PP = COD_CAMPUS+"_"+COD_CARRE+"_"+COD_CURSO+"_"+COD_SECCION+"_"+COD_FECHA+"_"+COD_HORA+"_"+COD_DOCEN;
                                            db.collection("clases").document(PP)
                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        DocumentSnapshot document = task.getResult();
                                                        if (Objects.requireNonNull(document).exists()) {
                                                            PROGRESO.dismiss();
                                                            Toast.makeText(AdminProgramarActivity.this, "Ya se encuentra registrado el curso", Toast.LENGTH_LONG).show();
                                                        }
                                                        else {
                                                            Map<String, Object> docData = new HashMap<>();
                                                            docData.put("DOCENTE", COD_DOCEN);
                                                            docData.put("INICIO", COD_HORA);
                                                            docData.put("FECHA", COD_FECHA);
                                                            db.collection("clases").document(PP)
                                                                    .set(docData)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            PROGRESO.dismiss();
                                                                            Toast.makeText(AdminProgramarActivity.this, "Se Registró la clase: " + PP, Toast.LENGTH_SHORT).show();
                                                                            onBackPressed();
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            PROGRESO.dismiss();
                                                                            Toast.makeText(AdminProgramarActivity.this, "No se Registró la clase: "+ PP, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                    } else {
                                                        PROGRESO.dismiss();
                                                        Toast.makeText(AdminProgramarActivity.this, "No se puso conectar satisfactoriamente " +  task.getException(), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                        else{
                                            PROGRESO.dismiss();
                                            Toast.makeText(this, "Se debe seleccionar un docente", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else{
                                        PROGRESO.dismiss();
                                        Toast.makeText(this, "Se debe seleccionar una duración", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    PROGRESO.dismiss();
                                    Toast.makeText(this, "Se debe seleccionar una hora de inicio", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                PROGRESO.dismiss();
                                Toast.makeText(this, "Se debe seleccionar una fecha", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            PROGRESO.dismiss();
                            Toast.makeText(this, "Se debe seleccionar una seccion", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        PROGRESO.dismiss();
                        Toast.makeText(this, "Se debe seleccionar un curso", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    PROGRESO.dismiss();
                    Toast.makeText(this, "Se debe seleccionar una carrera", Toast.LENGTH_LONG).show();
                }
            }
            else{
                PROGRESO.dismiss();
                Toast.makeText(this, "Se debe seleccionar un campus", Toast.LENGTH_LONG).show();
            }
        }
    }
}