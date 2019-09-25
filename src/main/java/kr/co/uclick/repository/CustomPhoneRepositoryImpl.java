package kr.co.uclick.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.QPhone;

public class CustomPhoneRepositoryImpl extends QuerydslRepositorySupport implements CustomPhoneRepository{

	
	private QPhone p = QPhone.phone;
	public CustomPhoneRepositoryImpl() {
		super(Phone.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Page<Phone> findDistinctUserByNumber(String number, Pageable pageable) {
		// TODO Auto-generated method stub

//		from(p).where(p.number.eq(number)).distinct().fetch();
		
		return null;
	}
	
	

}
