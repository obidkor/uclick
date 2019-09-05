package kr.co.uclick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.uclick.entity.QSample;
import kr.co.uclick.entity.Sample;
import kr.co.uclick.repository.SampleRepository;

@Service
@Transactional//springconfiguration에서 설정한 JpaTransactionManager를 사용하여 트랜잭션기능이 적용된 프록시 객체 생성함. 정상여부에 따라 commit,rollback함
public class SampleService {

	@Autowired
	private SampleRepository sampleRepository;

	public void delete(Sample entity) {
		sampleRepository.delete(entity);
	}

	@Transactional(readOnly = true)
	public List<Sample> findAll() {
		return sampleRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Sample> findSampleByName(String name) {

		sampleRepository.findAll(QSample.sample.name.eq(name));
		//원래는 querydsl을 이용해 Repo에 적어줘야하나 논리가 간단해 서비스에 작성했다???
		sampleRepository.doSample(name);

		return sampleRepository.findSampleByName(name);
	}

	public void save(Sample sample) {
		sampleRepository.save(sample);
	}

	public void findById(Long sampleId) {
		sampleRepository.findById(sampleId);
	}
}
