package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausDao;
import tikape.runko.domain.Kysymys;
import tikape.runko.domain.Vastaus;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:kysymys.db");
        database.init();

        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervetuloa kysymysjärjestelmään!");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymysDao.findAll());

            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());

        get("/kysymys/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymys", kysymysDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("vastaukset", vastausDao.findAll(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());
        
        get("/uusi_kysymys", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "uusi_kysymys");
        }, new ThymeleafTemplateEngine());
        
        post("/uusi_kysymys", (req, res) -> {
            String kurssi = req.queryParams("kurssi");
            String aihe = req.queryParams("aihe");
            String teksti = req.queryParams("teksti");
            
            kysymysDao.add(new Kysymys(0, kurssi, aihe, teksti));

            res.redirect("/kysymykset");
            return "";
        });
        
        get("/uusi_vastaus/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("id", req.params("id"));
            
            return new ModelAndView(map, "uusi_vastaus");
        }, new ThymeleafTemplateEngine());
        
        post("/uusi_vastaus/:id", (req, res) -> {
            String teksti = req.queryParams("teksti");
            Boolean oikein = req.queryParams("oikein") != null;
            Integer kysymys_id = Integer.parseInt(req.params("id"));
            
            vastausDao.add(new Vastaus(0, teksti, oikein, kysymys_id));

            res.redirect("/kysymys/" + req.params("id"));
            return "";
        });
        
        get("/poista_kysymys/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("id", req.params("id"));
            
            vastausDao.deleteAll(Integer.parseInt(req.params("id")));
            kysymysDao.delete(Integer.parseInt(req.params("id")));
            
            res.redirect("/kysymykset");
            return "";
        });
        
        get("/poista_vastaus/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("id", req.params("id"));
            Integer kysymys_id = vastausDao.findOne(Integer.parseInt(req.params("id"))).getKysymys();
            vastausDao.delete(Integer.parseInt(req.params("id")));
            
            res.redirect("/kysymys/" + kysymys_id);
            return "";
        });
        
        get("/katsominen", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymysDao.findAll());

            return new ModelAndView(map, "katsominen");
        }, new ThymeleafTemplateEngine());
    }
}
