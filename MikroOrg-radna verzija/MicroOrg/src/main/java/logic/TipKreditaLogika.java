package logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;




import viewModels.KreditniSluzbenik;
import domainModels.HibernateUtil;
import viewModels.TipKreditaSluzbenik;
import domainModels.TipKredita;
import domainModels.Osoba;
import domainModels.Uposlenik;

public class TipKreditaLogika {
	
	public Boolean daLiPostoji(String naziv) {
		
		Session _session= HibernateUtil.getSessionFactory().openSession();
		Transaction _t = _session.beginTransaction(); 
		 
		Criteria criteria = _session.createCriteria(TipKredita.class);
		TipKredita _tk =(TipKredita) criteria.add(Restrictions.eq("naziv", naziv)).uniqueResult();
		 
		_t.commit();
		_session.close();
		return _tk!=null;
	}
	
public Long dodajTipKredita(TipKreditaSluzbenik tipKredita) {
		
		//TODO: Uraditi hashiranje passworda
		Session _session= HibernateUtil.getSessionFactory().openSession();
		Transaction _t = _session.beginTransaction(); 
		 
		TipKredita _tk= new TipKredita(
				 
				 tipKredita.getNaziv(),
				 tipKredita.getNamjena(),
				 tipKredita.getIznos(),
				 tipKredita.getRok(),
				 tipKredita.getKamatnaStopa(),
				 tipKredita.getGarancija(),
				 null,
				 tipKredita.getGracePeriod(),
				 tipKredita.getTroskoviObrade()
				 
				 );
		 Long _id = (Long) _session.save(_tk); 
		  
		_t.commit(); 
		_session.close();
		return _id;
	}
public List<TipKreditaSluzbenik> getAll()
{
	List<TipKreditaSluzbenik> tipovi = new ArrayList <TipKreditaSluzbenik>();
	Session _session= HibernateUtil.getSessionFactory().openSession();
	Transaction _t = _session.beginTransaction(); 
	
	Criteria criteria = _session.createCriteria(TipKredita.class);
	 @SuppressWarnings("unchecked")
	 List<TipKredita> t =(List<TipKredita>) criteria.list();
	 for(TipKredita tip : t){
			
		 TipKreditaSluzbenik klijent= new TipKreditaSluzbenik(
				    tip.getNaziv(),
				    tip.getNamjena(),
				    tip.getIznos(),
				    tip.getRok(),
				    tip.getKamatnaStopa(),
				    tip.getGarancija(),
				    tip.getGracePeriod(),
				    tip.getTroskoviObrade()
				 );
		 tipovi.add(klijent);
	 }
	 _t.commit();
	 _session.close();
	 return tipovi;
}

 public TipKreditaSluzbenik getByName(String ime)
 {
	 TipKreditaSluzbenik tip = new TipKreditaSluzbenik();
	 Session _session= HibernateUtil.getSessionFactory().openSession();
	 Transaction _t = _session.beginTransaction();
	 Criteria criteria = _session.createCriteria(TipKredita.class);
	 @SuppressWarnings("unchecked")
	 TipKredita t =(TipKredita) criteria.add(Restrictions.eq("naziv", ime)).uniqueResult();
	 tip.setGarancija(t.getGarancija());
	 tip.setGracePeriod(t.getGracePeriod());
	 tip.setIznos(t.getIznos());
	 tip.setKamatnaStopa(t.getKamatnaStopa());
	 tip.setNamjena(t.getNamjena());
	 tip.setRok(t.getRok());
	 tip.setTroskoviObrade(t.getTroskoviObrade());
	 tip.setNaziv(ime);
	 return tip;
 }
 public List<TipKreditaSluzbenik> getByName2(String naziv) {
	 List<TipKreditaSluzbenik> _krediti= new ArrayList<TipKreditaSluzbenik>();
	
	 Session _session= HibernateUtil.getSessionFactory().openSession();
	 Transaction _t = _session.beginTransaction(); 
	 
	 Criteria criteria = _session.createCriteria(TipKredita.class);
	 @SuppressWarnings("unchecked")
	 
	 List<TipKredita> _kr =(List<TipKredita>) criteria.add(Restrictions.eq("naziv", naziv)).list();
	 for(TipKredita tk : _kr){
			
		 TipKreditaSluzbenik _kredit= new TipKreditaSluzbenik(
				    tk.getNaziv(),
				    tk.getNamjena(),
				 	tk.getIznos(),
				 	tk.getRok(),
					tk.getKamatnaStopa(),
					tk.getGarancija(),
					tk.getGracePeriod(),
					tk.getTroskoviObrade()
				 );
		 
			 _krediti.add(_kredit);
			// String naziv1= (String) _session.save(_kredit); 
	 }

	 _t.commit();
	 _session.close();
	 
	 return _krediti;
}
 public static List<TipKredita> dajSveKredite(){
		List<TipKredita> _krediti = new ArrayList<TipKredita>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
		       	tx = session.beginTransaction();
		       	List up = session.createQuery("FROM TipKredita").list(); 
		       	for (Iterator iterator = 
		               	up.iterator(); iterator.hasNext();){
		       		TipKredita tip = (TipKredita) iterator.next(); 
		       		_krediti.add(tip);
		       	}
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}
		finally {
			session.close(); 
		}
		return _krediti;
	}
 public void deleteByName(String naziv) {
	 Session _session= HibernateUtil.getSessionFactory().openSession();
	 Transaction _t = _session.beginTransaction(); 
	 
	 Criteria criteria = _session.createCriteria(TipKredita.class);
	 TipKredita _tk =(TipKredita) criteria.add(Restrictions.eq("naziv", naziv)).uniqueResult();
	// _tk.setAktivan(false);
	 
	 _session.delete(_tk);
	 _t.commit();
	 _session.close();
}
}