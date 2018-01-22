/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { GalleryMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/gallery-my-suffix/gallery-my-suffix-detail.component';
import { GalleryMySuffixService } from '../../../../../../main/webapp/app/entities/gallery-my-suffix/gallery-my-suffix.service';
import { GalleryMySuffix } from '../../../../../../main/webapp/app/entities/gallery-my-suffix/gallery-my-suffix.model';

describe('Component Tests', () => {

    describe('GalleryMySuffix Management Detail Component', () => {
        let comp: GalleryMySuffixDetailComponent;
        let fixture: ComponentFixture<GalleryMySuffixDetailComponent>;
        let service: GalleryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [GalleryMySuffixDetailComponent],
                providers: [
                    GalleryMySuffixService
                ]
            })
            .overrideTemplate(GalleryMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GalleryMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GalleryMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new GalleryMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.gallery).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
