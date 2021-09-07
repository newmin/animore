package com.proj.animore.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.proj.animore.dto.BusinessDTO;
import com.proj.animore.dto.BusinessLoadDTO;
import com.proj.animore.dto.HtagBusiListReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BusinessDAOImpl implements BusinessDAO {

	private final JdbcTemplate jdbcTemplate;
	//업체가입
	@Override
	public void joinBusi(BusinessDTO businessDTO) {
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
	//업체조회
	@Override
	public BusinessLoadDTO findBusiByBnum(Integer bnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select bnum,bname,baddress,btel,openhours");
		sql.append(" from business ");
		sql.append("where bnum = ? ");
		BusinessLoadDTO businessLoadDTO = jdbcTemplate.queryForObject(sql.toString(),
				new BeanPropertyRowMapper<>(BusinessLoadDTO.class),
				bnum);
		
		
		return businessLoadDTO;
	}

	//업체정보수정
	@Override
	public BusinessLoadDTO modifyBusi(Integer bnum, BusinessDTO businessDTO) {
		StringBuilder sql = new StringBuilder();
		sql.append("update business ");
		sql.append("   set bname = ?, ");
		sql.append("       baddress = ?, ");
		sql.append("       btel = ? ");
		sql.append(" where bnum = ? ");
		
		jdbcTemplate.update(
				sql.toString(), 
				businessDTO.getBname(),
				businessDTO.getBaddress(),
				businessDTO.getBtel()
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
		
		List<BusinessLoadDTO> list = jdbcTemplate.query(sql.toString(),
					   new BeanPropertyRowMapper<>(BusinessLoadDTO.class));
		
		log.info(list.toString());
		
		return list;
	}
	//회원은 즐겨찾기 상단고정하여 목록조회
	@Override
	public List<BusinessLoadDTO> busiListForMember(String bcategory, String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.BNUM,b.BBNUM,b.BNAME,b.BADDRESS,b.BTEL,b.NIGHTCARE,b.RAREANI,b.VISITCARE,b.HOLIDAYOPEN,b.DENTAL, r.bscore ");
		sql.append("  from business b left join favorite f on b.bnum=f.bnum and f.id = '"+id+"', bcategory c, (select bnum, round(avg(rscore),2) bscore ");
		sql.append("                                from review ");
		sql.append("                                group by bnum) r ");
		sql.append(" where b.bnum=c.bnum ");
		sql.append("   and b.bnum=r.bnum ");
		sql.append("   and "+bcategory+" = 'Y' ");
		sql.append(" order by fdate desc nulls last ");
		
		List<BusinessLoadDTO> list = jdbcTemplate.query(sql.toString(),
				   new BeanPropertyRowMapper<>(BusinessLoadDTO.class));
	
		log.info(list.toString());
	
		return list;
	}
	
	@Override
	public List<BusinessLoadDTO> busiListBySearch(String text) {
		StringBuffer sql = new StringBuffer();
		return null;
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
	
	
}
