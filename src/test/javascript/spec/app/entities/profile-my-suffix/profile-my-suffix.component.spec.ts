/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { ProfileMySuffixComponent } from '../../../../../../main/webapp/app/entities/profile-my-suffix/profile-my-suffix.component';
import { ProfileMySuffixService } from '../../../../../../main/webapp/app/entities/profile-my-suffix/profile-my-suffix.service';
import { ProfileMySuffix } from '../../../../../../main/webapp/app/entities/profile-my-suffix/profile-my-suffix.model';

describe('Component Tests', () => {

    describe('ProfileMySuffix Management Component', () => {
        let comp: ProfileMySuffixComponent;
        let fixture: ComponentFixture<ProfileMySuffixComponent>;
        let service: ProfileMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [ProfileMySuffixComponent],
                providers: [
                    ProfileMySuffixService
                ]
            })
            .overrideTemplate(ProfileMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfileMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfileMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new ProfileMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.profiles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
