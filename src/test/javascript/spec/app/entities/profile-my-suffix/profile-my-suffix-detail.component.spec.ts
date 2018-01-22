/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { ProfileMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/profile-my-suffix/profile-my-suffix-detail.component';
import { ProfileMySuffixService } from '../../../../../../main/webapp/app/entities/profile-my-suffix/profile-my-suffix.service';
import { ProfileMySuffix } from '../../../../../../main/webapp/app/entities/profile-my-suffix/profile-my-suffix.model';

describe('Component Tests', () => {

    describe('ProfileMySuffix Management Detail Component', () => {
        let comp: ProfileMySuffixDetailComponent;
        let fixture: ComponentFixture<ProfileMySuffixDetailComponent>;
        let service: ProfileMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [ProfileMySuffixDetailComponent],
                providers: [
                    ProfileMySuffixService
                ]
            })
            .overrideTemplate(ProfileMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfileMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfileMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ProfileMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.profile).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
