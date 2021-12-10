package tn.esprit.spring.services.livreur;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import tn.esprit.spring.entities.Livreur;
import tn.esprit.spring.repositories.LivreurRepository;

@Slf4j
@Service
public class LvreurServiceImpl implements LivreurServiceImpl{

	@Autowired
	private LivreurRepository livreurRepository;
	
	@Override
	public HashMap<String,Object> livreurspagination(Integer pageNo, Integer pageSize,  String filter) {
		// TODO Auto-generated method stub
		PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by("etat").and(Sort.by("dateAdhesion").descending()));
        //Page<Livreur> pagedResult = livreurRepository.findAll(paging);
		//PageRequest paging = PageRequest.of(pageNo, pageSize);
		Page<Livreur> pagedResult = null;
		if(filter!=null && !filter.equals("")) {
			log.info("not null");
			pagedResult = livreurRepository.filterLivreurList(filter, paging); 
		}else {
			pagedResult = livreurRepository.findAll(paging); 
			log.info("null");
		}
		HashMap<String, Object> map = new HashMap<>();
        if(pagedResult.hasContent()) {
    		map.put("livreurs", pagedResult.getContent());
    		map.put("total", livreurRepository.retrieveLvreursCount());
        } else {
        	map.put("livreurs", pagedResult.getContent());
    		map.put("total", 0);
    	}
        return map;
	}
	
	@Override
	public List<Livreur> retrieveAllLivreurs() {
		// TODO Auto-generated method stub
		return (List<Livreur>) livreurRepository.findAll();
	}
	
	@Override
	public List<Livreur> retrieveActiveLivreurs() {
		// TODO Auto-generated method stub
		return livreurRepository.retrieveActiveLivreurs();
	}
 
	@Override
	public Livreur addLivreur(Livreur l) {
		// TODO Auto-generated method stub
		l.setDateAdhesion(new Date(System.currentTimeMillis()));
		return livreurRepository.save(l);
	}

	@Override
	public void deleteLivreur(Long id) {
		// TODO Auto-generated method stub
		livreurRepository.deleteById(id);
	}

	@Override
	public Livreur updateLivreur(Livreur l) {
		// TODO Auto-generated method stub
		return livreurRepository.save(l);
	}

	@Override
	public Livreur retrieveLivreur(Long id) {
		// TODO Auto-generated method stub
		return livreurRepository.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public HashMap<String,Object> disableLivreursWithIds(List<Long> ids) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", "update successful");
		livreurRepository.disableLivreursWithIds(ids);
		return map;
	}

	@Override
	public Page<Livreur> filterLivreurList(String filter, Pageable pageable) {
		// TODO Auto-generated method stub
		return livreurRepository.filterLivreurList(filter, pageable);
	}

}
