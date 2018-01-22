/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { ImageMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/image-my-suffix/image-my-suffix-detail.component';
import { ImageMySuffixService } from '../../../../../../main/webapp/app/entities/image-my-suffix/image-my-suffix.service';
import { ImageMySuffix } from '../../../../../../main/webapp/app/entities/image-my-suffix/image-my-suffix.model';

describe('Component Tests', () => {

    describe('ImageMySuffix Management Detail Component', () => {
        let comp: ImageMySuffixDetailComponent;
        let fixture: ComponentFixture<ImageMySuffixDetailComponent>;
        let service: ImageMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [ImageMySuffixDetailComponent],
                providers: [
                    ImageMySuffixService
                ]
            })
            .overrideTemplate(ImageMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ImageMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImageMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ImageMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.image).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
