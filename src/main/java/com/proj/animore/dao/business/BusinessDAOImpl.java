package com.proj.animore.dao.business;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.business.BusinessDTO;
import com.proj.animore.dto.business.BusinessLoadDTO;
import com.proj.animore.dto.business.HtagBusiListReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BusinessDAOImpl implements BusinessDAO {

	private final JdbcTemplate jdbcTemplate;
	//업체가입
	@Override
	public int joinBusi(BusinessDTO businessDTO) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into business(bnum, bbnum, id, bname, baddress, btel, openhours,nightcare,rareani,visitcare,holidayopen,dental) values(business_bnum_seq.nextval,?,?,?,?,?,?,?,?,?,?,?) ");
		
		jdbcTemplate.update(sql.toString(),
												businessDTO.getBbnum(),
												businessDTO.getId(),
												businessDTO.getBname(),
												businessDTO.getBaddress(),
												businessDTO.getBtel(),
												businessDTO.getOpenhours(),
												businessDTO.getNightcare(),
												businessDTO.getRareani(),
												businessDTO.getVisitcare(),
												businessDTO.getHolidayopen(),
												businessDTO.getDental());
		
		return bnumCurrVal();
	}
	//업체조회
	@Override
	public BusinessLoadDTO findBusiByBnum(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select bnum,bname,baddress,btel,openhours,id ");
		sql.append(" from business ");
		sql.append(" where bnum = ? ");
		
		BusinessLoadDTO businessLoadDTO = jdbcTemplate.queryForObject(sql.toString(),
				new BeanPropertyRowMapper<>(BusinessLoadDTO.class),
				bnum);
		
		return businessLoadDTO;
	}

	//업체정보수정
	@Override
	public BusinessLoadDTO modifyBusi(Integer bnum, BusinessLoadDTO businessLoadDTO) {
		StringBuilder sql = new StringBuilder();
		sql.append("update business ");
		sql.append("   set bname = ?, ");
		sql.append("       baddress = ?, ");
		sql.append("       btel = ? ");
		sql.append(" where bnum = ? ");
		
		log.info(sql.toString());
		
		jdbcTemplate.update(
				sql.toString(), 
				businessLoadDTO.getBname(),
				businessLoadDTO.getBaddress(),
				businessLoadDTO.getBtel(),
				bnum
				);
		return findBusiByBnum(bnum);
	}

	//업체정보 삭제
	@Override
	public void deleteBusi(Integer bnum) {
		// TODO Auto-generated method stub
		
	}

	//업체목록 조회
	@Override
	public List<BusinessLoadDTO> busiList(String bcategory) {
		StringBuffer sql = new StringBuffer();

		sql.append("select b.BNUM,b.BBNUM,b.BNAME,b.BADDRESS,b.BTEL,b.NIGHTCARE,b.RAREANI,b.VISITCARE,b.HOLIDAYOPEN,b.DENTAL, r.bscore  ");
		sql.append("from business b, bcategory c, (select bnum, round(avg(rscore),2) bscore ");
		sql.append("                                from review ");
		sql.append("                                group by bnum) r  ");
		sql.append("where b.bnum=c.bnum  ");
		sql.append("  and b.bnum=r.bnum(+)	  ");
		sql.append("  and "+bcategory+" = 'Y'  ");
		sql.append(" order by r.bscore desc nulls last ");
		
		List<BusinessLoadDTO> list = jdbcTemplate.query(sql.toString(),
					   new BeanPropertyRowMapper<>(BusinessLoadDTO.class));
		
		log.info(list.toString());
		
		return list;
	}
	//회원은 즐겨찾기 상단고정하여 목록조회
	@Override
	public List<BusinessLoadDTO> busiListForMember(String bcategory, String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.BNUM,b.BBNUM,b.BNAME,b.BADDRESS,b.BTEL,r.bscore,b.NIGHTCARE,b.RAREANI,b.VISITCARE,b.HOLIDAYOPEN,b.DENTAL ");
		sql.append("  from business b left join favorite f on b.bnum=f.bnum and f.id = '"+id+"' "
				+ "left join (select bnum,round(avg(rscore),2) bscore from review  group by bnum) r on b.bnum=r.bnum, bcategory c ");
		sql.append(" where b.bnum=c.bnum ");
//		sql.append("   and b.bnum=r.bnum(+) ");
		sql.append("   and "+bcategory+" = 'Y'  ");
		sql.append("order by fdate desc , r.bscore desc nulls last ");
		
		List<BusinessLoadDTO> list = jdbcTemplate.query(sql.toString(),
				   new BeanPropertyRowMapper<>(BusinessLoadDTO.class));
	
		log.info(list.toString());
	
		return list;
	}
	//검색어로 업체조회
	@Override
	public List<BusinessLoadDTO> busiListBySearch(String search) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.BNUM,b.BBNUM,b.BNAME,b.BADDRESS,b.BTEL,b.NIGHTCARE,b.RAREANI,b.VISITCARE,b.HOLIDAYOPEN,b.DENTAL, r.bscore  ");
		sql.append("from business b, (select bnum, round(avg(rscore),2) bscore ");
		sql.append("                                from review ");
		sql.append("                                group by bnum) r  ");
		sql.append(" where b.bnum=r.bnum(+)	  ");
		sql.append("   and (BNAME like '%"+search+"%' ");
		sql.append("    or BADDRESS like '%"+search+"%') ");

		List<BusinessLoadDTO> list = jdbcTemplate.query(sql.toString(),
				   new BeanPropertyRowMapper<>(BusinessLoadDTO.class));

		return list;
	}
	
	
	
	//업체추가등록
	public void addBusi(BusinessDTO businessDTO) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("insert into business(bnum, bbnum, id, bname, baddress, btel, openhours) values(business_bnum_seq.nextval,?,?,?,?,?,?) ");
		
		jdbcTemplate.update(sql.toString(),
												businessDTO.getBbnum(),
												businessDTO.getId(),
												businessDTO.getBname(),
												businessDTO.getBaddress(),
												businessDTO.getBtel(),
												businessDTO.getOpenhours());
	}
	
	//업체목록 조회
	@Override
	public List<BusinessLoadDTO> busiListHospitalTag(String bcategory, HtagBusiListReq htblr) {
		StringBuffer sql = new StringBuffer();

															sql.append("select b.BNUM,b.BBNUM,b.BNAME,b.BADDRESS,b.BTEL,b.NIGHTCARE,b.RAREANI,b.VISITCARE,b.HOLIDAYOPEN,b.DENTAL, r.bscore  ");
															sql.append("from business b, bcategory c, (select bnum, round(avg(rscore),2) bscore ");
															sql.append("                                from review ");
															sql.append("                                group by bnum) r  ");
															sql.append("where b.bnum=c.bnum  ");
															sql.append("  and b.bnum=r.bnum(+)	  ");
															sql.append("  and "+bcategory+" = 'Y'  ");
		if(htblr.isNightcare())		sql.append("  and nightcare = 'Y' ");
		if(htblr.isRareani())			sql.append("  and rareani = 'Y' ");
		if(htblr.isVisitcare())		sql.append("  and visitcare = 'Y' ");
		if(htblr.isHolidayopen())	sql.append("  and holidayopen = 'Y' ");	
		if(htblr.isDental())			sql.append("  and dental = 'Y' ");

		List<BusinessLoadDTO> list = jdbcTemplate.query(sql.toString(),
					   new BeanPropertyRowMapper<>(BusinessLoadDTO.class));
		
		log.info(list.toString());
		
		return list;
	}
	
	
	//내업체 목록
	@Override
	public List<BusinessLoadDTO> mybusiList(String id) {
		
		StringBuffer sql = new StringBuffer();

		sql.append(" select rownum, x.* ");
		sql.append(" from (select b.bname, b.baddress ,b.btel ,b.bnum");
		sql.append(" from business b ");
		sql.append("  where id = ?) x  ");
		
		List<BusinessLoadDTO> mybusiList = jdbcTemplate.query(sql.toString(),
					   new BeanPropertyRowMapper<>(BusinessLoadDTO.class),id
					   );
		
		log.info(mybusiList.toString());
		
		return mybusiList;
	}
	
	@Override
	public BusinessLoadDTO findBusiById(String id) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select bnum,bname,baddress,btel,id");
		sql.append(" from business ");
		sql.append(" where id = ? ");
		BusinessLoadDTO businessLoadDTO = jdbcTemplate.queryForObject(sql.toString(),
				new BeanPropertyRowMapper<>(BusinessLoadDTO.class),
				id);
		
		return businessLoadDTO;
	}
	
	@Override
	public Integer bnumCurrVal() {
		String sql = "select business_bnum_seq.currval from dual ";
		int bnum = jdbcTemplate.queryForObject(sql,Integer.class);
		return bnum;
	}
	
}
