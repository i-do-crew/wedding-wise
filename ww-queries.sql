select vc.title, v.title, v.street_address, v.city, v.state, v.zip, v.about, vp.title, vp.description
from vendors v
join users u on v.user_id = u.id
join vendor_categories vc on v.category_id = vc.id
join vendor_packages vp on v.id = vp.vendor_id
where v.id = 1;

select vp.title, vp.description
from vendors v
join vendor_packages vp on v.id = vp.vendor_id
where v.id = 1;

select vs.title as service
from vendors v
join vendor_services vs on v.id = vs.vendor_id
where v.id = 1;