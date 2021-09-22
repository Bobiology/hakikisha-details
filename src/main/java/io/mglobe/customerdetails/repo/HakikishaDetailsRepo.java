package io.mglobe.customerdetails.repo;

import io.mglobe.customerdetails.models.response.HakikishaDetailsResponse;


//@Repository
public interface HakikishaDetailsRepo //extends JpaRepository<HakikishaDetailsRequest, Long>
{
	HakikishaDetailsResponse getResponse();
}
