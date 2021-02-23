package com.folders.rentaly.controller;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.folders.rentaly.controller.wrapper.SearchWrapper;
import com.folders.rentaly.model.Realty;
import com.folders.rentaly.model.SavedSearch;
import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.dao.RealtyDAO;
import com.folders.rentaly.persistence.dao.SavedSearchDAO;
import com.folders.rentaly.service.CustomUserDetailService;
import com.folders.rentaly.service.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequestMapping("/search")
public class SearchController {

    @Value("${search.maxdistance}")
    private Integer MAXDISTANCE;

    @Value("${search.maxprice}")
    private Integer MAXPRICE;

    @Autowired
    private RealtyDAO realtyDAO;

    @Autowired
    private SavedSearchDAO savedSearchDAO;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private StorageService storageService;

    @GetMapping("")
    public ModelAndView doGetSearch(ModelAndView model, @RequestParam(required = false) Map<String,String> values){
        model.setViewName("search");

        model.addObject("global_maxdistance", MAXDISTANCE);
        model.addObject("global_maxprice", MAXPRICE);

        model.addObject("minprice", Integer.valueOf(0));
        model.addObject("maxprice", MAXPRICE);
        model.addObject("distance", Integer.valueOf(10));
        String query = values.get("search-query");
        if (query == null)
            query = "";
        model.addObject("searchquery", query);

        if (!values.isEmpty() && values.containsKey("latitude") && values.containsKey("longitude")) {
            model.addObject("content", doGetSearchList(new ModelAndView(), values));
        }

        return model;
    }

    @GetMapping("/internal")
    public ModelAndView doGetSearchList(ModelAndView model, @RequestParam Map<String,String> values) {
        log.info(values.toString());
        model.setViewName("searchResult");

        Double latitude = null; 
        Double longitude = null;
        try {
            latitude = Double.parseDouble(values.get("latitude"));
            longitude = Double.parseDouble(values.get("longitude"));
        } catch (Exception e)  {}

        String type = values.get("type");

        if (latitude != null && longitude != null) {
            Integer distance = 10;

            try {
                distance = Integer.parseInt(values.get("distance"));
            } catch (Exception e) {}

            distance = Math.min(distance, MAXDISTANCE);

            Float minPrice = 0.0f;

            try {
                minPrice = Float.parseFloat(values.get("min_price"));
            } catch (Exception e) {}

            Float maxPrice = Float.MAX_VALUE;

            try {
                maxPrice = Float.parseFloat(values.get("max_price"));
            } catch (Exception e) {}

            List<Realty> result = null;

            if (type == null || type.contains("Qualsiasi") || type.equals("")) {
                result = realtyDAO.findClosestToPoint(latitude, longitude, distance);
                model.addObject("type", new String("Qualsiasi"));
            }
            else {
                result = realtyDAO.findClosestToPointAndType(latitude, longitude, distance, type);
                model.addObject("type", type);
            }

            List<SearchWrapper> realties = new LinkedList<>();
            for (Realty r : result) {
                realties.add(new SearchWrapper(r,storageService.getAllById(r.getInsertion().getId())));
            }
            model.addObject("realties", realties);

            model.addObject("minprice", minPrice);
            model.addObject("maxprice", maxPrice);
            model.addObject("distance", distance);
            model.addObject("latitude", latitude);
            model.addObject("longitude", longitude);

            String query = values.get("search-query");
            if (query == null)
                query = "";
            model.addObject("searchquery", query);

        }

        return model;
    }

    @PostMapping(value = "/save", consumes = { "application/json" })
	@ResponseBody
	public ResponseEntity<String> doSaveSearch(HttpSession session, @RequestBody SavedSearch search) {
        Optional<User> u = customUserDetailService.getUser(session);
		if (u.isPresent()) {
            search.setUser(u.get());
            if( search.getType() == null || search.getType().equals("")) {
                search.setType("Qualsiasi");
            }
            savedSearchDAO.save(search);

			return new ResponseEntity<>("success", HttpStatus.OK);
        }

		return ResponseEntity.badRequest().body("error");
	}
}